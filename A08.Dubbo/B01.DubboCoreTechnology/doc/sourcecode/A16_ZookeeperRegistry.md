## ZookeeperRegistry

#### 1 简介

> 向zk注册消费者和提供者的注册器。



#### 2 类关系

> 使用zk做注册中心，

```

AbstractRegistry (org.apache.dubbo.registry.support)
    FailbackRegistry (org.apache.dubbo.registry.support)
        DubboRegistry (org.apache.dubbo.registry.dubbo)
        ConsulRegistry (org.apache.dubbo.registry.consul)
        ZookeeperRegistry (org.apache.dubbo.registry.zookeeper) # zk注册器
        EtcdRegistry (org.apache.dubbo.registry.etcd)
        MulticastRegistry (org.apache.dubbo.registry.multicast)
        RedisRegistry (org.apache.dubbo.registry.redis)

```


#### 3 AbstractRegistry


##### 3.1 AbstractRegistry#subscribe

```
 public void subscribe(URL url, NotifyListener listener) {
    if (url == null) {
        throw new IllegalArgumentException("subscribe url == null");
    }
    if (listener == null) {
        throw new IllegalArgumentException("subscribe listener == null");
    }
    if (logger.isInfoEnabled()) {
        logger.info("Subscribe: " + url);
    }
    Set<NotifyListener> listeners = subscribed.computeIfAbsent(url, n -> new ConcurrentHashSet<>());
    listeners.add(listener);
}

```


##### 3.2 AbstractRegistry#notify

> 通知

```
protected void notify(URL url, NotifyListener listener, List<URL> urls) {
    if (url == null) {
        throw new IllegalArgumentException("notify url == null");
    }
    if (listener == null) {
        throw new IllegalArgumentException("notify listener == null");
    }
    if ((CollectionUtils.isEmpty(urls))
            && !Constants.ANY_VALUE.equals(url.getServiceInterface())) {
        logger.warn("Ignore empty notify urls for subscribe url " + url);
        return;
    }
    if (logger.isInfoEnabled()) {
        logger.info("Notify urls for subscribe url " + url + ", urls: " + urls);
    }
    // keep every provider's category.
    Map<String, List<URL>> result = new HashMap<>();
    for (URL u : urls) {
        if (UrlUtils.isMatch(url, u)) {
            String category = u.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY);
            List<URL> categoryList = result.computeIfAbsent(category, k -> new ArrayList<>());
            categoryList.add(u);
        }
    }
    if (result.size() == 0) {
        return;
    }
    Map<String, List<URL>> categoryNotified = notified.computeIfAbsent(url, u -> new ConcurrentHashMap<>());
    for (Map.Entry<String, List<URL>> entry : result.entrySet()) {
        String category = entry.getKey();
        List<URL> categoryList = entry.getValue();
        categoryNotified.put(category, categoryList);
        // 注意这个listener就是RegistryDirectory这个类
        // 订阅方法也是从RegistryDirectory，订阅完后，又给自己进行通知
        listener.notify(categoryList);
        // We will update our cache file after each notification.
        // When our Registry has a subscribe failure due to network jitter, we can return at least the existing cache URL.
        saveProperties(url);
    }
}





```

#### 4 FailbackRegistry

##### 4.1 FailbackRegistry#subscribe


```
// 注意这个listener是RegistryDirectory
public void subscribe(URL url, NotifyListener listener) {
    // 这里先调用父类的subscribe方法
    super.subscribe(url, listener);
    removeFailedSubscribed(url, listener);
    try {
        // Sending a subscription request to the server side
        // 发送订阅请求到服务方，这里执行的是子类的方法，比如ZookeeperRegistry#doSubscribe
        doSubscribe(url, listener);
    } catch (Exception e) {
        Throwable t = e;

        List<URL> urls = getCacheUrls(url);
        if (CollectionUtils.isNotEmpty(urls)) {
            notify(url, listener, urls);
            logger.error("Failed to subscribe " + url + ", Using cached list: " + urls + " from cache file: " + getUrl().getParameter(Constants.FILE_KEY, System.getProperty("user.home") + "/dubbo-registry-" + url.getHost() + ".cache") + ", cause: " + t.getMessage(), t);
        } else {
            // If the startup detection is opened, the Exception is thrown directly.
            boolean check = getUrl().getParameter(Constants.CHECK_KEY, true)
                    && url.getParameter(Constants.CHECK_KEY, true);
            boolean skipFailback = t instanceof SkipFailbackWrapperException;
            if (check || skipFailback) {
                if (skipFailback) {
                    t = t.getCause();
                }
                throw new IllegalStateException("Failed to subscribe " + url + ", cause: " + t.getMessage(), t);
            } else {
                logger.error("Failed to subscribe " + url + ", waiting for retry, cause: " + t.getMessage(), t);
            }
        }

        // Record a failed registration request to a failed list, retry regularly
        addFailedSubscribed(url, listener);
    }
}



```

##### 4.2 FailbackRegistry#notify


```
// 这个listener是RegistryDirectory
@Override
protected void notify(URL url, NotifyListener listener, List<URL> urls) {
    if (url == null) {
        throw new IllegalArgumentException("notify url == null");
    }
    if (listener == null) {
        throw new IllegalArgumentException("notify listener == null");
    }
    try {
        doNotify(url, listener, urls);
    } catch (Exception t) {
        // Record a failed registration request to a failed list, retry regularly
        addFailedNotified(url, listener, urls);
        logger.error("Failed to notify for subscribe " + url + ", waiting for retry, cause: " + t.getMessage(), t);
    }
}



protected void doNotify(URL url, NotifyListener listener, List<URL> urls) {
    // 调用AbstractRegistry#notify
    super.notify(url, listener, urls);
}




```




#### 5 ZookeeperRegistry

##### 5.1 ZookeeperRegistry#doSubscribe


```
// 这个listener是RegistryDirectory
@Override
public void doSubscribe(final URL url, final NotifyListener listener) {
    try {
        if (Constants.ANY_VALUE.equals(url.getServiceInterface())) {
            String root = toRootPath();
            ConcurrentMap<NotifyListener, ChildListener> listeners = zkListeners.get(url);
            if (listeners == null) {
                zkListeners.putIfAbsent(url, new ConcurrentHashMap<>());
                listeners = zkListeners.get(url);
            }
            ChildListener zkListener = listeners.get(listener);
            if (zkListener == null) {
                listeners.putIfAbsent(listener, (parentPath, currentChilds) -> {
                    for (String child : currentChilds) {
                        child = URL.decode(child);
                        if (!anyServices.contains(child)) {
                            anyServices.add(child);
                            subscribe(url.setPath(child).addParameters(Constants.INTERFACE_KEY, child,
                                    Constants.CHECK_KEY, String.valueOf(false)), listener);
                        }
                    }
                });
                zkListener = listeners.get(listener);
            }
            zkClient.create(root, false);
            List<String> services = zkClient.addChildListener(root, zkListener);
            if (CollectionUtils.isNotEmpty(services)) {
                for (String service : services) {
                    service = URL.decode(service);
                    anyServices.add(service);
                    subscribe(url.setPath(service).addParameters(Constants.INTERFACE_KEY, service,
                            Constants.CHECK_KEY, String.valueOf(false)), listener);
                }
            }
        } else {
            List<URL> urls = new ArrayList<>();
            for (String path : toCategoriesPath(url)) {
                ConcurrentMap<NotifyListener, ChildListener> listeners = zkListeners.get(url);
                if (listeners == null) {
                    zkListeners.putIfAbsent(url, new ConcurrentHashMap<>());
                    listeners = zkListeners.get(url);
                }
                ChildListener zkListener = listeners.get(listener);
                if (zkListener == null) {
                    listeners.putIfAbsent(listener, (parentPath, currentChilds) -> ZookeeperRegistry.this.notify(url, listener, toUrlsWithEmpty(url, parentPath, currentChilds)));
                    zkListener = listeners.get(listener);
                }
                zkClient.create(path, false);
                List<String> children = zkClient.addChildListener(path, zkListener);
                if (children != null) {
                    urls.addAll(toUrlsWithEmpty(url, path, children));
                }
            }
            // 父类的通知方法FailbackRegistry#notify
            // 这个listener是RegistryDirectory
            // 继续往下跟踪，则可以看到最后还是调用了RegistryDirectory的notify方法
            notify(url, listener, urls);
        }
    } catch (Throwable e) {
        throw new RpcException("Failed to subscribe " + url + " to zookeeper " + getUrl() + ", cause: " + e.getMessage(), e);
    }
}




```

