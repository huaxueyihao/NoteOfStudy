## RegistryDirectory 

#### 1 简介

> 注册目录：管理Invoker列表。



#### 2 RegistryDirectory#subscribe

> 订阅注册中心的提供者

```

public void subscribe(URL url) {
    setConsumerUrl(url);
    consumerConfigurationListener.addNotifyListener(this);
    serviceConfigurationListener = new ReferenceConfigurationListener(this, url);
    // 这里的registry=ZookeeperRegistry
    // 第二个参数是个NotifyListener类型的监听者，而RegistryDirectory实现了NotifyListener
    registry.subscribe(url, this);
}


```




#### 3 RegistryDirectory#notify

> 通知

```

public synchronized void notify(List<URL> urls) {
    
    Map<String, List<URL>> categoryUrls = urls.stream()
            .filter(Objects::nonNull)
            .filter(this::isValidCategory)
            .filter(this::isNotCompatibleFor26x)
            .collect(Collectors.groupingBy(url -> {
                if (UrlUtils.isConfigurator(url)) {
                    return CONFIGURATORS_CATEGORY;
                } else if (UrlUtils.isRoute(url)) {
                    return ROUTERS_CATEGORY;
                } else if (UrlUtils.isProvider(url)) {
                    return PROVIDERS_CATEGORY;
                }
                return "";
            }));

    // 配置信息，比如服务降级信息
    List<URL> configuratorURLs = categoryUrls.getOrDefault(CONFIGURATORS_CATEGORY, Collections.emptyList());
    this.configurators = Configurator.toConfigurators(configuratorURLs).orElse(this.configurators);

    // 路由信息收集并保存
    List<URL> routerURLs = categoryUrls.getOrDefault(ROUTERS_CATEGORY, Collections.emptyList());
    toRouters(routerURLs).ifPresent(this::addRouters);

    // providers
    // 服务提供者信息
    List<URL> providerURLs = categoryUrls.getOrDefault(PROVIDERS_CATEGORY, Collections.emptyList());
    refreshOverrideAndInvoker(providerURLs);
}


private void refreshOverrideAndInvoker(List<URL> urls) {
    // mock zookeeper://xxx?mock=return null
    overrideDirectoryUrl();
    // 刷新invokers列表
    refreshInvoker(urls);
}


// 刷新Invoker集合
private void refreshInvoker(List<URL> invokerUrls) {
    Assert.notNull(invokerUrls, "invokerUrls should not be null");

    if (invokerUrls.size() == 1
            && invokerUrls.get(0) != null
            && Constants.EMPTY_PROTOCOL.equals(invokerUrls.get(0).getProtocol())) {
        this.forbidden = true; // Forbid to access
        this.invokers = Collections.emptyList();
        routerChain.setInvokers(this.invokers);
        destroyAllInvokers(); // Close all invokers
    } else {
        this.forbidden = false; // Allow to access
        Map<String, Invoker<T>> oldUrlInvokerMap = this.urlInvokerMap; // local reference
        if (invokerUrls == Collections.<URL>emptyList()) {
            invokerUrls = new ArrayList<>();
        }
        if (invokerUrls.isEmpty() && this.cachedInvokerUrls != null) {
            invokerUrls.addAll(this.cachedInvokerUrls);
        } else {
            this.cachedInvokerUrls = new HashSet<>();
            this.cachedInvokerUrls.addAll(invokerUrls);//Cached invoker urls, convenient for comparison
        }
        if (invokerUrls.isEmpty()) {
            return;
        }
        // 将URL转成Invoker集合
        Map<String, Invoker<T>> newUrlInvokerMap = toInvokers(invokerUrls);// Translate url list to Invoker map

        /**
         * If the calculation is wrong, it is not processed.
         *
         * 1. The protocol configured by the client is inconsistent with the protocol of the server.
         *    eg: consumer protocol = dubbo, provider only has other protocol services(rest).
         * 2. The registration center is not robust and pushes illegal specification data.
         *
         */
        if (CollectionUtils.isEmptyMap(newUrlInvokerMap)) {
            logger.error(new IllegalStateException("urls to invokers error .invokerUrls.size :" + invokerUrls.size() + ", invoker.size :0. urls :" + invokerUrls
                    .toString()));
            return;
        }

        List<Invoker<T>> newInvokers = Collections.unmodifiableList(new ArrayList<>(newUrlInvokerMap.values()));
        // pre-route and build cache, notice that route cache should build on original Invoker list.
        // toMergeMethodInvokerMap() will wrap some invokers having different groups, those wrapped invokers not should be routed.
        routerChain.setInvokers(newInvokers);
        this.invokers = multiGroup ? toMergeInvokerList(newInvokers) : newInvokers;
        this.urlInvokerMap = newUrlInvokerMap;

        try {
            destroyUnusedInvokers(oldUrlInvokerMap, newUrlInvokerMap); // Close the unused Invoker
        } catch (Exception e) {
            logger.warn("destroyUnusedInvokers error. ", e);
        }
    }
}




private Map<String, Invoker<T>> toInvokers(List<URL> urls) {
    // 
    Map<String, Invoker<T>> newUrlInvokerMap = new HashMap<>();
    if (urls == null || urls.isEmpty()) {
        return newUrlInvokerMap;
    }
    Set<String> keys = new HashSet<>();
    String queryProtocols = this.queryMap.get(Constants.PROTOCOL_KEY);
    for (URL providerUrl : urls) {
        // If protocol is configured at the reference side, only the matching protocol is selected
        if (queryProtocols != null && queryProtocols.length() > 0) {
            boolean accept = false;
            String[] acceptProtocols = queryProtocols.split(",");
            for (String acceptProtocol : acceptProtocols) {
                if (providerUrl.getProtocol().equals(acceptProtocol)) {
                    accept = true;
                    break;
                }
            }
            if (!accept) {
                continue;
            }
        }
        if (Constants.EMPTY_PROTOCOL.equals(providerUrl.getProtocol())) {
            continue;
        }
        if (!ExtensionLoader.getExtensionLoader(Protocol.class).hasExtension(providerUrl.getProtocol())) {
            logger.error(new IllegalStateException("Unsupported protocol " + providerUrl.getProtocol() +
                    " in notified url: " + providerUrl + " from registry " + getUrl().getAddress() +
                    " to consumer " + NetUtils.getLocalHost() + ", supported protocol: " +
                    ExtensionLoader.getExtensionLoader(Protocol.class).getSupportedExtensions()));
            continue;
        }
        URL url = mergeUrl(providerUrl);

        String key = url.toFullString(); // The parameter urls are sorted
        if (keys.contains(key)) { // Repeated url
            continue;
        }
        keys.add(key);
        // Cache key is url that does not merge with consumer side parameters, regardless of how the consumer combines parameters, if the server url changes, then refer again
        Map<String, Invoker<T>> localUrlInvokerMap = this.urlInvokerMap; // local reference
        Invoker<T> invoker = localUrlInvokerMap == null ? null : localUrlInvokerMap.get(key);
        if (invoker == null) { // Not in the cache, refer again
            try {
                boolean enabled = true;
                if (url.hasParameter(Constants.DISABLED_KEY)) {
                    enabled = !url.getParameter(Constants.DISABLED_KEY, false);
                } else {
                    enabled = url.getParameter(Constants.ENABLED_KEY, true);
                }
                if (enabled) {
                    invoker = new InvokerDelegate<>(protocol.refer(serviceType, url), url, providerUrl);
                }
            } catch (Throwable t) {
                logger.error("Failed to refer invoker for interface:" + serviceType + ",url:(" + url + ")" + t.getMessage(), t);
            }
            if (invoker != null) { // Put new invoker in cache
                newUrlInvokerMap.put(key, invoker);
            }
        } else {
            newUrlInvokerMap.put(key, invoker);
        }
    }
    keys.clear();
    return newUrlInvokerMap;
}


```








