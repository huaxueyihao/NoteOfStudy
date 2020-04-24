## ReferenceConfig 

#### 1 简介

> 服务消费方的配置类，实际上就是服务消费方的主要实现类


#### 2 类关系

```

AbstractConfig (org.apache.dubbo.config)
    AbstractMethodConfig (org.apache.dubbo.config) 
        MethodConfig (org.apache.dubbo.config)
        AbstractInterfaceConfig (org.apache.dubbo.config)
            AbstractServiceConfig (org.apache.dubbo.config)
                ProviderConfig (org.apache.dubbo.config)
                ServiceConfig (org.apache.dubbo.config) #目标类
            AbstractReferenceConfig (org.apache.dubbo.config)
                ReferenceConfig (org.apache.dubbo.config)
                ConsumerConfig (org.apache.dubbo.config)
    ConfigCenterConfig (org.apache.dubbo.config)
        ConfigCenterBean (org.apache.dubbo.config.spring)
    ApplicationConfig (org.apache.dubbo.config)
        ApplicationConfig (com.alibaba.dubbo.config)
    RegistryConfig (org.apache.dubbo.config)
        RegistryConfig (com.alibaba.dubbo.config)
    ProtocolConfig (org.apache.dubbo.config)
        ProtocolConfig (com.alibaba.dubbo.config)
    ModuleConfig (org.apache.dubbo.config)
        ModuleConfig (com.alibaba.dubbo.config)
    MonitorConfig (org.apache.dubbo.config)
        MonitorConfig (com.alibaba.dubbo.config)
    MetadataReportConfig (org.apache.dubbo.config)


```
> 顶层类是AbstractConfig，所有的配置类都是其子类


#### 3 RefenceConfig使用demo

> 1.consumer.xml，文件放在resources文件夹下

```

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">


    <!--    提供方应用信息-->
    <dubbo:application name="hello-consumer"/>

    <dubbo:registry address="zookeeper://127.0.0.1:2181"/>


    <dubbo:reference id="helloService" interface="com.books.dubbo.service.HelloService" />

</beans>



```

> 2.ApiConsumer

```
package org.apache.dubbo.demo.consumer;

import com.books.dubbo.demo.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

public class ApiConsumer {


    public static void main(String[] args) throws InterruptedException {

        // 10.创建服务引用对象实例
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<GreetingService>();
        // 11.设置应用程序信息
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        // 12.设置服务注册中心
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        // 直连测试
        // referenceConfig.setUrl("dubbo://192.168.0.109:20880");

        // 13.设置服务接口和超时时间
        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setTimeout(5000);

        // 14.设置自定义负载均衡策略与集群错策略
//        referenceConfig.setLoadbalance("myroundrobin");
//        referenceConfig.setCluster("myCluster");
//        RpcContext.getContext().set("ip", "30.10.67.231");


        // 15.设置服务分组与版本
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        // 16.引用服务
        GreetingService greetingService = referenceConfig.get();

        // 17.设置隐式参数
        RpcContext.getContext().setAttachment("company", "alibaba");

        // 18.调用服务
        System.out.println(greetingService.sayHello("world"));
        Thread.currentThread().join();



    }


}



```



#### 4 RefenceConfig#get

> 消费方获得代理服务的方法

```


public synchronized T get() {
    // 校验更新
    checkAndUpdateSubConfigs();

    if (destroyed) {
        throw new IllegalStateException("The invoker of ReferenceConfig(" + url + ") has already destroyed!");
    }
    if (ref == null) {
        init();
    }
    return ref;
}


private void init() {
    if (initialized) {
        return;
    }
    initialized = true;
    checkStubAndLocal(interfaceClass);
    checkMock(interfaceClass);
    // 请求参数拼接
    Map<String, String> map = new HashMap<String, String>();

    map.put(Constants.SIDE_KEY, Constants.CONSUMER_SIDE);
    appendRuntimeParameters(map);
    if (!isGeneric()) {
        String revision = Version.getVersion(interfaceClass, version);
        if (revision != null && revision.length() > 0) {
            map.put("revision", revision);
        }

        String[] methods = Wrapper.getWrapper(interfaceClass).getMethodNames();
        if (methods.length == 0) {
            logger.warn("No method found in service interface " + interfaceClass.getName());
            map.put("methods", Constants.ANY_VALUE);
        } else {
            map.put("methods", StringUtils.join(new HashSet<String>(Arrays.asList(methods)), ","));
        }
    }
    map.put(Constants.INTERFACE_KEY, interfaceName);
    appendParameters(map, application);
    appendParameters(map, module);
    appendParameters(map, consumer, Constants.DEFAULT_KEY);
    appendParameters(map, this);
    Map<String, Object> attributes = null;
    if (CollectionUtils.isNotEmpty(methods)) {
        attributes = new HashMap<String, Object>();
        for (MethodConfig methodConfig : methods) {
            appendParameters(map, methodConfig, methodConfig.getName());
            String retryKey = methodConfig.getName() + ".retry";
            if (map.containsKey(retryKey)) {
                String retryValue = map.remove(retryKey);
                if ("false".equals(retryValue)) {
                    map.put(methodConfig.getName() + ".retries", "0");
                }
            }
            attributes.put(methodConfig.getName(), convertMethodConfig2AyncInfo(methodConfig));
        }
    }

    String hostToRegistry = ConfigUtils.getSystemProperty(Constants.DUBBO_IP_TO_REGISTRY);
    if (StringUtils.isEmpty(hostToRegistry)) {
        hostToRegistry = NetUtils.getLocalHost();
    }
    map.put(Constants.REGISTER_IP_KEY, hostToRegistry);

    // 创建代理
    ref = createProxy(map);

    String serviceKey = URL.buildKey(interfaceName, group, version);
    ApplicationModel.initConsumerModel(serviceKey, buildConsumerModel(serviceKey, attributes));
}




```


#### 4 RefenceConfig#createProxy


```



private T createProxy(Map<String, String> map) {
    // 是不是本地服务
    if (shouldJvmRefer(map)) {
        // 本地服务
        URL url = new URL(Constants.LOCAL_PROTOCOL, Constants.LOCALHOST_VALUE, 0, interfaceClass.getName()).addParameters(map);
        invoker = refprotocol.refer(interfaceClass, url);
        if (logger.isInfoEnabled()) {
            logger.info("Using injvm service " + interfaceClass.getName());
        }
    } else {
        // 远程服务
        if (url != null && url.length() > 0) { // user specified URL, could be peer-to-peer address, or register center's address.
            String[] us = Constants.SEMICOLON_SPLIT_PATTERN.split(url);
            if (us != null && us.length > 0) {
                for (String u : us) {
                    URL url = URL.valueOf(u);
                    if (StringUtils.isEmpty(url.getPath())) {
                        url = url.setPath(interfaceName);
                    }
                    if (Constants.REGISTRY_PROTOCOL.equals(url.getProtocol())) {
                        urls.add(url.addParameterAndEncoded(Constants.REFER_KEY, StringUtils.toQueryString(map)));
                    } else {
                        urls.add(ClusterUtils.mergeUrl(url, map));
                    }
                }
            }
        } else { // assemble URL from register center's configuration
            // 检查注册中心
            checkRegistry();
            // 加载注册中心地址
            List<URL> us = loadRegistries(false);
            if (CollectionUtils.isNotEmpty(us)) {
                for (URL u : us) {
                    URL monitorUrl = loadMonitor(u);
                    if (monitorUrl != null) {
                        map.put(Constants.MONITOR_KEY, URL.encode(monitorUrl.toFullString()));
                    }
                    urls.add(u.addParameterAndEncoded(Constants.REFER_KEY, StringUtils.toQueryString(map)));
                }
            }
            if (urls.isEmpty()) {
                throw new IllegalStateException("No such any registry to reference " + interfaceName + " on the consumer " + NetUtils.getLocalHost() + " use dubbo version " + Version.getVersion() + ", please config <dubbo:registry address=\"...\" /> to your spring config.");
            }
        }

        // 单台注册中心
        if (urls.size() == 1) {
             // 根据协议获得Invoker对象，refprotocol仍然是Protocol$Adaptive
             // 但是这个协议是是RegistryProtocol
             // 所以执行的是RegistryProtocol的refer方法 
            invoker = refprotocol.refer(interfaceClass, urls.get(0));
        } else {
            // 多态注册中心服务
            List<Invoker<?>> invokers = new ArrayList<Invoker<?>>();
            URL registryURL = null;
            for (URL url : urls) {
                invokers.add(refprotocol.refer(interfaceClass, url));
                // 根据协议类型获得提供服务的注册中心的URL对象
                if (Constants.REGISTRY_PROTOCOL.equals(url.getProtocol())) {
                    registryURL = url; // use last registry url
                }
            }
            if (registryURL != null) { // registry url is available
                // use RegistryAwareCluster only when register's cluster is available
                URL u = registryURL.addParameter(Constants.CLUSTER_KEY, RegistryAwareCluster.NAME);
                // The invoker wrap relation would be: RegistryAwareClusterInvoker(StaticDirectory) -> FailoverClusterInvoker(RegistryDirectory, will execute route) -> Invoker
                invoker = cluster.join(new StaticDirectory(u, invokers));
            } else { // not a registry url, must be direct invoke.
                invoker = cluster.join(new StaticDirectory(invokers));
            }
        }
    }

    if (shouldCheck() && !invoker.isAvailable()) {
        // make it possible for consumer to retry later if provider is temporarily unavailable
        initialized = false;
        throw new IllegalStateException("Failed to check the status of the service " + interfaceName + ". No provider available for the service " + (group == null ? "" : group + "/") + interfaceName + (version == null ? "" : ":" + version) + " from the url " + invoker.getUrl() + " to the consumer " + NetUtils.getLocalHost() + " use dubbo version " + Version.getVersion());
    }
    if (logger.isInfoEnabled()) {
        logger.info("Refer dubbo service " + interfaceClass.getName() + " from url " + invoker.getUrl());
    }
    /**
     * @since 2.7.0
     * ServiceData Store
     */
    MetadataReportService metadataReportService = null;
    if ((metadataReportService = getMetadataReportService()) != null) {
        URL consumerURL = new URL(Constants.CONSUMER_PROTOCOL, map.remove(Constants.REGISTER_IP_KEY), 0, map.get(Constants.INTERFACE_KEY), map);
        metadataReportService.publishConsumer(consumerURL);
    }
    // create service proxy
    // 根据invoker获得代理对象
    // 这里的proxyFactory还是JavassistProxyFactory
    return (T) proxyFactory.getProxy(invoker);
}


```
