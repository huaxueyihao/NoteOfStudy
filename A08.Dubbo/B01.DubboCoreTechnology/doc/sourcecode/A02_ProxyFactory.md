## ProxyFactory 

#### 1 简介

> 代理工厂：提供了三个方法

```

/**
     * create proxy.
     * 创建服务代理
     * @param invoker
     * @return proxy
     */
    @Adaptive({Constants.PROXY_KEY})
    <T> T getProxy(Invoker<T> invoker) throws RpcException;

    /**
     * create proxy.
     * 创建服务代理
     * @param invoker
     * @return proxy
     */
    @Adaptive({Constants.PROXY_KEY})
    <T> T getProxy(Invoker<T> invoker, boolean generic) throws RpcException;

    /**
     * create invoker.
     * 服务转化为Invoker对象，这里创建Invoker
     * @param <T>
     * @param proxy
     * @param type
     * @param url
     * @return invoker
     */
    @Adaptive({Constants.PROXY_KEY})
    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException;



```


#### 2 类关系

```

ProxyFactory (com.alibaba.dubbo.rpc)
StubProxyFactoryWrapper (org.apache.dubbo.rpc.proxy.wrapper)
AbstractProxyFactory (org.apache.dubbo.rpc.proxy)
    JavassistProxyFactory (org.apache.dubbo.rpc.proxy.javassist) 
    JdkProxyFactory (org.apache.dubbo.rpc.proxy.jdk)


```

> 主要是JavassistProxyFactory和JdkProxyFactory的实现，主要使用的是JavassistProxyFactory




#### 3 ServiceConfig字段proxyFactory


```
private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

```

> 在上述字段处debug，可以得到proxyFactory是个类型为ProxyFactory$Adaptive类，这个类是动态生成的，具体如何生成先不研究；
  我们先看看这个生成的类型内容
  
> 可以看到这个类是ProxyFactory的实现类，
  
```


package org.apache.dubbo.rpc;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.RpcException;

public class ProxyFactory$Adaptive
implements ProxyFactory {
    public Object getProxy(Invoker invoker) throws RpcException {
        if (invoker == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.Invoker argument == null");
        }
        if (invoker.getUrl() == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.Invoker argument getUrl() == null");
        }
        URL uRL = invoker.getUrl();
        String string = uRL.getParameter("proxy", "javassist");
        if (string == null) {
            throw new IllegalStateException(new StringBuffer().append("Failed to get extension (org.apache.dubbo.rpc.ProxyFactory) name from url (").append(uRL.toString()).append(") use keys([proxy])").toString());
        }
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(string);
        return proxyFactory.getProxy(invoker);
    }

    public Object getProxy(Invoker invoker, boolean bl) throws RpcException {
        if (invoker == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.Invoker argument == null");
        }
        if (invoker.getUrl() == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.Invoker argument getUrl() == null");
        }
        URL uRL = invoker.getUrl();
        String string = uRL.getParameter("proxy", "javassist");
        if (string == null) {
            throw new IllegalStateException(new StringBuffer().append("Failed to get extension (org.apache.dubbo.rpc.ProxyFactory) name from url (").append(uRL.toString()).append(") use keys([proxy])").toString());
        }
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(string);
        return proxyFactory.getProxy(invoker, bl);
    }

    public Invoker getInvoker(Object object, Class class_, URL uRL) throws RpcException {
        if (uRL == null) {
            throw new IllegalArgumentException("url == null");
        }
        URL uRL2 = uRL;
        // URL.("proxy", "javassist")是从URL的parameters(参数Map)获取key为proxy的值，
        // 没有值就使用"javassist"作为返回值
        
        String string = uRL2.getParameter("proxy", "javassist");
        if (string == null) {
            throw new IllegalStateException(new StringBuffer().append("Failed to get extension (org.apache.dubbo.rpc.ProxyFactory) name from url (").append(uRL2.toString()).append(") use keys([proxy])").toString());
        }
        // 这里根据名称获得ProxyFactory类，根据javassist我们已经猜出来，若是没有指定proxy的值，就是返回javassist，大胆猜测就是JavassistProxyFactory这个类
        // 也就是ProxyFactory$Adaptive动态生成的类最后执行的还是JavassistProxyFactory的方法，单词Adaptive适配的意思，其实这个类的作用就是在
        // JavassistProxyFactory和JdkProxyFactory之间做一个适配
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(string);
        return proxyFactory.getInvoker(object, class_, uRL);
    }
}


```



#### 4 proxyFactory#getInvoker

> 先看ServiceConfig#exportLocal代码，debug可以看点变量local中的parameters字段中是否有key=proxy，若是没有配置则是没有的

```


private void exportLocal(URL url) {
    if (!Constants.LOCAL_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
        //URL中 parameters(参数Map)没有key=proxy的值，
        URL local = URLBuilder.from(url)
                .setProtocol(Constants.LOCAL_PROTOCOL)
                .setHost(LOCALHOST_VALUE)
                .setPort(0)
                .build();
        // proxyFactory=ProxyFactory$Adaptive
        // 实际也就是调用JavassistProxyFactory的getInvoker
        Exporter<?> exporter = protocol.export(
                proxyFactory.getInvoker(ref, (Class) interfaceClass, local));
        exporters.add(exporter);
        logger.info("Export dubbo service " + interfaceClass.getName() + " to local registry");
    }
}

```


##### 4.1 JavassistProxyFactory#getInvoker

> getInvoker 这个方法目的是根据URL对象装换成一个Invoker对象

```

public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
    // TODO Wrapper cannot handle this scenario correctly: the classname contains '$'
    // Wrapper类前面已经说过是生成一个暴露的服务GreettingService的包装类，暂不先研究此类，Wrapper会生成一个
    // proxy名称中如果含有$符说明是动态生成的类
    final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);
    
    // 这里直接直接new了个抽象代理Invoker类
    return new AbstractProxyInvoker<T>(proxy, type, url) {
    
        // Invoker的doInvoke方法，也就是当消费者来消费提供者的时候，会来调用这个doInvoke方法
        // doInvoke方法执行的还是Wrapper的invokeMethod方法
        // 上面说过Wrapper是暴露的服务的包装类，也就是最终是调用到GreettingService的服务
        @Override
        protected Object doInvoke(T proxy, String methodName,
                                  Class<?>[] parameterTypes,
                                  Object[] arguments) throws Throwable {
            return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
        }
    };
}



```


> 综上：ProxyFactory这个代理工厂，是用来将要暴露的服务(如GreettingService)生成各种代理的服务Invoker，
       Invoker的doInvoke方法，最终执行的是Wrapper的invokeMethod方法
