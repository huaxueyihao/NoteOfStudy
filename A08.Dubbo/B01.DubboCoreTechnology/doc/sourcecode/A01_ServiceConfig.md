

## ServiceConfig 

#### 1 简介

> 服务提供方的配置类，实际上就是服务提供者的主要实现类


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


#### 3 serviceConfig使用demo

> 1.provider.xml，文件放在resources文件夹下

```

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">


    <!--    提供方应用信息-->
    <dubbo:application name="hello-provider"/>

    <dubbo:registry address="zookeeper://127.0.0.1:2181"/>

    <dubbo:protocol name="dubbo" port="20880"/>

    <bean id="helloService" class="com.books.dubbo.service.impl.HelloServiceImpl"/>

    <dubbo:service interface="com.books.dubbo.service.HelloService" ref="helloService"/>


</beans>


```

> 2.ApiProvider

```

package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;
import java.util.HashMap;

public class ApiProvider {

    public static void main(String[] args) throws IOException {

        // 1.创建ServiceConfig实例
        ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<GreetingService>();
        // 2.设置应用程序配置
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));

        // 3.设置服务注册中心信息
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        serviceConfig.setRegistry(registryConfig);

        // 4.设置接口与实现类
        serviceConfig.setInterface(GreetingService.class);
        serviceConfig.setRef(new GreettingServiceImpl());

        // 5.设置服务分组与版本
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("dubbo");
        // 6.设置线程策略
//        HashMap<String, String> parameters = new HashMap<String, String>();
//        parameters.put("threadpool", "mythreadpool");
//        serviceConfig.setParameters(parameters);

        // 7.导出服务
        serviceConfig.export();
        // 8.挂起线程，避免服务停止
        System.out.println("server is started");
        System.in.read();


    }

}


```


#### 4 调试ServiceConfig#export方法


```

public synchronized void export() {
    // 校验工作和更新配置，主要完成一件事，启动一个线程，
    // 定时动态的去拉取配置文件，然后刷新配置信息
    checkAndUpdateSubConfigs();

    // 判断是否将要暴露
    if (!shouldExport()) {
        return;
    }

    // 是否延迟暴露，如果配置了延迟时间，则会延迟暴露
    if (shouldDelay()) {
        delayExportExecutor.schedule(this::doExport, delay, TimeUnit.MILLISECONDS);
    } else {
        // 暴露的方法
        doExport();
    }
}



```

##### 4.1 ServiceConfig#checkAndUpdateSubConfigs

> 主要工作是完成动态获取配置文件，以及启动一个后台线程定时去获取配置文件

```

public void checkAndUpdateSubConfigs() {
        // Use default configs defined explicitly on global configs
        // 完成配置信息生成
        completeCompoundConfigs();
        // Config Center should always being started first.
        startConfigCenter();
        checkDefault();
        checkApplication();
        // 这里启动动态配置线程，其实里面还是调用了startConfigCenter()方法
        checkRegistry();
        checkProtocol();
        this.refresh();
        checkMetadataReport();

        if (StringUtils.isEmpty(interfaceName)) {
            throw new IllegalStateException("<dubbo:service interface=\"\" /> interface not allow null!");
        }

        // 是否是泛化引用的服务
        if (ref instanceof GenericService) {
            interfaceClass = GenericService.class;
            if (StringUtils.isEmpty(generic)) {
                generic = Boolean.TRUE.toString();
            }
        } else {
            try {
                interfaceClass = Class.forName(interfaceName, true, Thread.currentThread()
                        .getContextClassLoader());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            checkInterfaceAndMethods(interfaceClass, methods);
            checkRef();
            generic = Boolean.FALSE.toString();
        }
        if (local != null) {
            if ("true".equals(local)) {
                local = interfaceName + "Local";
            }
            Class<?> localClass;
            try {
                localClass = ClassHelper.forNameWithThreadContextClassLoader(local);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            if (!interfaceClass.isAssignableFrom(localClass)) {
                throw new IllegalStateException("The local implementation class " + localClass.getName() + " not implement interface " + interfaceName);
            }
        }
        if (stub != null) {
            if ("true".equals(stub)) {
                stub = interfaceName + "Stub";
            }
            Class<?> stubClass;
            try {
                stubClass = ClassHelper.forNameWithThreadContextClassLoader(stub);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            if (!interfaceClass.isAssignableFrom(stubClass)) {
                throw new IllegalStateException("The stub implementation class " + stubClass.getName() + " not implement interface " + interfaceName);
            }
        }
        checkStubAndLocal(interfaceClass);
        checkMock(interfaceClass);
    }



```


##### 4.2 ServiceConfig#doExport


```

protected synchronized void doExport() {
    if (unexported) {
        throw new IllegalStateException("The service " + interfaceClass.getName() + " has already unexported!");
    }
    
    // 是否已经暴露过
    if (exported) {
        return;
    }
    exported = true;

    if (StringUtils.isEmpty(path)) {
        path = interfaceName;
    }
    doExportUrls();
}


private void doExportUrls() {
    // 加载注册中心的列表，返回URL结果集
    List<URL> registryURLs = loadRegistries(true);
    // 根据不同的协议进行对外暴露服务
    for (ProtocolConfig protocolConfig : protocols) {
        String pathKey = URL.buildKey(getContextPath(protocolConfig).map(p -> p + "/" + path).orElse(path), group, version);
        ProviderModel providerModel = new ProviderModel(pathKey, ref, interfaceClass);
        ApplicationModel.initProviderModel(pathKey, providerModel);
        // 进行暴露的方法
        doExportUrlsFor1Protocol(protocolConfig, registryURLs);
    }
}


```


##### 4.4 ServiceConfig#doExportUrlsFor1Protocol

```

private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig, List<URL> registryURLs) {
    String name = protocolConfig.getName();
    if (StringUtils.isEmpty(name)) {
        name = Constants.DUBBO;
    }

    // 处理一些参数，这些参数将要是会转成URL对象的一部分
    Map<String, String> map = new HashMap<String, String>();
    map.put(Constants.SIDE_KEY, Constants.PROVIDER_SIDE);
    appendRuntimeParameters(map);
    appendParameters(map, application);
    appendParameters(map, module);
    appendParameters(map, provider, Constants.DEFAULT_KEY);
    appendParameters(map, protocolConfig);
    appendParameters(map, this);
    if (CollectionUtils.isNotEmpty(methods)) {
        for (MethodConfig method : methods) {
            appendParameters(map, method, method.getName());
            String retryKey = method.getName() + ".retry";
            if (map.containsKey(retryKey)) {
                String retryValue = map.remove(retryKey);
                if ("false".equals(retryValue)) {
                    map.put(method.getName() + ".retries", "0");
                }
            }
            List<ArgumentConfig> arguments = method.getArguments();
            if (CollectionUtils.isNotEmpty(arguments)) {
                for (ArgumentConfig argument : arguments) {
                    // convert argument type
                    if (argument.getType() != null && argument.getType().length() > 0) {
                        Method[] methods = interfaceClass.getMethods();
                        // visit all methods
                        if (methods != null && methods.length > 0) {
                            for (int i = 0; i < methods.length; i++) {
                                String methodName = methods[i].getName();
                                // target the method, and get its signature
                                if (methodName.equals(method.getName())) {
                                    Class<?>[] argtypes = methods[i].getParameterTypes();
                                    // one callback in the method
                                    if (argument.getIndex() != -1) {
                                        if (argtypes[argument.getIndex()].getName().equals(argument.getType())) {
                                            appendParameters(map, argument, method.getName() + "." + argument.getIndex());
                                        } else {
                                            throw new IllegalArgumentException("Argument config error : the index attribute and type attribute not match :index :" + argument.getIndex() + ", type:" + argument.getType());
                                        }
                                    } else {
                                        // multiple callbacks in the method
                                        for (int j = 0; j < argtypes.length; j++) {
                                            Class<?> argclazz = argtypes[j];
                                            if (argclazz.getName().equals(argument.getType())) {
                                                appendParameters(map, argument, method.getName() + "." + j);
                                                if (argument.getIndex() != -1 && argument.getIndex() != j) {
                                                    throw new IllegalArgumentException("Argument config error : the index attribute and type attribute not match :index :" + argument.getIndex() + ", type:" + argument.getType());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (argument.getIndex() != -1) {
                        appendParameters(map, argument, method.getName() + "." + argument.getIndex());
                    } else {
                        throw new IllegalArgumentException("Argument config must set index or type attribute.eg: <dubbo:argument index='0' .../> or <dubbo:argument type=xxx .../>");
                    }

                }
            }
        } // end of methods for
    }

    // 判断是否泛化引用，
    if (ProtocolUtils.isGeneric(generic)) {
        map.put(Constants.GENERIC_KEY, generic);
        map.put(Constants.METHODS_KEY, Constants.ANY_VALUE);
    } else {
        String revision = Version.getVersion(interfaceClass, version);
        if (revision != null && revision.length() > 0) {
            map.put("revision", revision);
        }

        // 这里的Wrapper是针对要暴露的服务进行的包装，且这wrapper是动态生成的类，这里是获得暴露服务里的方法
        // GreettingServiceImpl里面两个方法：sayHello、testGeneric
        String[] methods = Wrapper.getWrapper(interfaceClass).getMethodNames();
        if (methods.length == 0) {
            logger.warn("No method found in service interface " + interfaceClass.getName());
            map.put(Constants.METHODS_KEY, Constants.ANY_VALUE);
        } else {
            map.put(Constants.METHODS_KEY, StringUtils.join(new HashSet<String>(Arrays.asList(methods)), ","));
        }
    }
    
    if (!ConfigUtils.isEmpty(token)) {
        if (ConfigUtils.isDefault(token)) {
            map.put(Constants.TOKEN_KEY, UUID.randomUUID().toString());
        } else {
            map.put(Constants.TOKEN_KEY, token);
        }
    }
    // export service
    // 暴露服务
    String host = this.findConfigedHosts(protocolConfig, registryURLs, map);
    Integer port = this.findConfigedPorts(protocolConfig, name, map);
    // 将上述参数组合生成URL对象
    URL url = new URL(name, host, port, getContextPath(protocolConfig).map(p -> p + "/" + path).orElse(path), map);

    if (ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class)
            .hasExtension(url.getProtocol())) {
        url = ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class)
                .getExtension(url.getProtocol()).getConfigurator(url).configure(url);
    }

    String scope = url.getParameter(Constants.SCOPE_KEY);
    // don't export when none is configured
    // 判断服务暴露的范围，是进行远程不漏，还是同一个机器内的本地jvm内暴露
    if (!Constants.SCOPE_NONE.equalsIgnoreCase(scope)) {

        // export to local if the config is not remote (export to remote only when config is remote)
        if (!Constants.SCOPE_REMOTE.equalsIgnoreCase(scope)) {
            // 本地暴露
            exportLocal(url);
        }
        // export to remote if the config is not local (export to local only when config is local)
        if (!Constants.SCOPE_LOCAL.equalsIgnoreCase(scope)) {
            if (logger.isInfoEnabled()) {
                logger.info("Export dubbo service " + interfaceClass.getName() + " to url " + url);
            }
            if (CollectionUtils.isNotEmpty(registryURLs)) {
                // 这里就是远程服务暴露过程
                for (URL registryURL : registryURLs) {
                    url = url.addParameterIfAbsent(Constants.DYNAMIC_KEY, registryURL.getParameter(Constants.DYNAMIC_KEY));
                    URL monitorUrl = loadMonitor(registryURL);
                    if (monitorUrl != null) {
                        url = url.addParameterAndEncoded(Constants.MONITOR_KEY, monitorUrl.toFullString());
                    }
                    if (logger.isInfoEnabled()) {
                        logger.info("Register dubbo service " + interfaceClass.getName() + " url " + url + " to registry " + registryURL);
                    }

                    // For providers, this is used to enable custom proxy to generate invoker
                    String proxy = url.getParameter(Constants.PROXY_KEY);
                    if (StringUtils.isNotEmpty(proxy)) {
                        registryURL = registryURL.addParameter(Constants.PROXY_KEY, proxy);
                    }

                    // 代码和本地暴露基本差不多
                    Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, registryURL.addParameterAndEncoded(Constants.EXPORT_KEY, url.toFullString()));
                    // 这里和本地暴露就是多了这行代码，也就是Invoker被DelegateProviderMetaDataInvoker包装了一次，DelegateProviderMetaDataInvoker持有了ServiceConfig(元数据)引用
                    DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);

                    Exporter<?> exporter = protocol.export(wrapperInvoker);
                    exporters.add(exporter);
                }
            } else {
                Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, url);
                DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);

                Exporter<?> exporter = protocol.export(wrapperInvoker);
                exporters.add(exporter);
            }
            /**
             * @since 2.7.0
             * ServiceData Store
             */
            MetadataReportService metadataReportService = null;
            if ((metadataReportService = getMetadataReportService()) != null) {
                metadataReportService.publishProvider(url);
            }
        }
    }
    this.urls.add(url);
}




```


##### 4.5 ServiceConfig#exportLocal

```

private void exportLocal(URL url) {
    if (!Constants.LOCAL_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
        URL local = URLBuilder.from(url)
                .setProtocol(Constants.LOCAL_PROTOCOL)
                .setHost(LOCALHOST_VALUE)
                .setPort(0)
                .build();
        // 将URL对象先转换成Invoker对象，然后Invoker对象转成Exporter，对外暴露的服务就是Exporter
        // proxyFactory#getInvoker后面分析
        Exporter<?> exporter = protocol.export(
                proxyFactory.getInvoker(ref, (Class) interfaceClass, local));
        exporters.add(exporter);
        logger.info("Export dubbo service " + interfaceClass.getName() + " to local registry");
    }
}


```
