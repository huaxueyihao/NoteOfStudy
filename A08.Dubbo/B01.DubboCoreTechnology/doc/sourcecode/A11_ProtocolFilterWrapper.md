### ProtocolFilterWrapper、ProtocolListenerWrapper、QosProtocolWrapper

#### 1 简介

> 在服务暴露过程中，对DubboProtocol进行的包装类，注意这里是对协议进行的包装类，可以看到这几个类都是
  Protocol的子类。这里主要分析这几个类是什么时候加载的，以及什么时候对DubboProtocol进行包装的。
  主要是ServiceConfig的Protocol属性为切入点
  
  
  
#### 2 ServiceConfig的Protocol属性

> 前面分析ExtensionLoader的时候，就是从这个属性为切入点来分析过Protocol的通过spi机制加载的一个过程
  主要是以getAdaptiveExtension这个方法进行了深入分析，

  
  

```

private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();


```

> ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
  这个方法根据SPI机制完成了加载META-INF/dubbo/internal/org.apache.dubbo.rpc.protocol.Protocol文件中关于
  Protocol的扩展类，文件内容如下：其中有三个Wrapper，就是此篇文中的几个类
  
  
```

filter=org.apache.dubbo.rpc.protocol.ProtocolFilterWrapper
listener=org.apache.dubbo.rpc.protocol.ProtocolListenerWrapper
mock=org.apache.dubbo.rpc.support.MockProtocol
dubbo=org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol
injvm=org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol
rmi=org.apache.dubbo.rpc.protocol.rmi.RmiProtocol
hessian=org.apache.dubbo.rpc.protocol.hessian.HessianProtocol
http=org.apache.dubbo.rpc.protocol.http.HttpProtocol

org.apache.dubbo.rpc.protocol.webservice.WebServiceProtocol
thrift=org.apache.dubbo.rpc.protocol.thrift.ThriftProtocol
memcached=org.apache.dubbo.rpc.protocol.memcached.MemcachedProtocol
redis=org.apache.dubbo.rpc.protocol.redis.RedisProtocol
rest=org.apache.dubbo.rpc.protocol.rest.RestProtocol
registry=org.apache.dubbo.registry.integration.RegistryProtocol
qos=org.apache.dubbo.qos.protocol.QosProtocolWrapper




```

> 到这里其实结合之前分析的getAdaptiveExtension方法，我们已经知道，这几个类肯定是这个方法进行的加载，
  这里简单捋一下，主要参照ExtensionLoader的篇幅分析。
  我们简单的吧这个过程分4步


```

getAdaptiveExtension() #方法获得适配扩展类。
    createAdaptiveExtension() #方法创建适配扩展类
        getAdaptiveExtensionClass() #通过SPI机制加载META-INF/dubbo/internal/org.apache.dubbo.rpc.protocol.Protocol里面的扩展类
        injectExtension((T) getAdaptiveExtensionClass().newInstance()) #将上一步或得到的扩展类对象进行setter方法依赖注入操作。


```

> 上面4步中，getAdaptiveExtensionClass()方法完成了扩展类的加载，我们再深入捋一下这个方法


```

getAdaptiveExtensionClass()
    getExtensionClasses() # 获得扩展类
        loadExtensionClasses() # 加载
               loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY, type.getName()); #根据文件路径进行加载
                   loadResource(extensionClasses, classLoader, resourceURL); 
                        loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name);



```

> 上述方法层级较深，主要是为了到loadClass这个方法，展开这方法，第二个if(isWrapperClass(clazz))这里就是本文中三个Wrapper加载进来的时机和过程

```

private void loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz, String name) throws NoSuchMethodException {
    if (!type.isAssignableFrom(clazz)) {
        throw new IllegalStateException("Error occurred when loading extension class (interface: " +
                type + ", class line: " + clazz.getName() + "), class "
                + clazz.getName() + " is not subtype of interface.");
    }
    // 类上有Adaptive注解
    if (clazz.isAnnotationPresent(Adaptive.class)) {
        // 缓存，直接赋值给cachedAdaptiveClass
        cacheAdaptiveClass(clazz);
    } else if (isWrapperClass(clazz)) {
        // 是包装类的
        // 缓存
        cacheWrapperClass(clazz);
    } else {
        clazz.getConstructor();
        if (StringUtils.isEmpty(name)) {
            name = findAnnotationName(clazz);
            if (name.length() == 0) {
                throw new IllegalStateException("No such extension name for the class " + clazz.getName() + " in the config " + resourceURL);
            }
        }

        String[] names = NAME_SEPARATOR.split(name);
        if (ArrayUtils.isNotEmpty(names)) {
            cacheActivateClass(clazz, names[0]);
            for (String n : names) {
                // 缓存
                cacheName(clazz, n);
                // 缓存到
                saveInExtensionClass(extensionClasses, clazz, name);
            }
        }
    }
}


private boolean isWrapperClass(Class<?> clazz) {
    try {
        // 根据构造函数的入参来判断是否是Wrapper类
        // 比如
        // public ProtocolFilterWrapper(Protocol protocol) {
        //       if (protocol == null) {
        //             throw new IllegalArgumentException("protocol == null");
        //       }
        //       this.protocol = protocol;
        // }
        // 可以看到ProtocolFilterWrapper的构造函数的入参类型是protocol
        // 可以断点到这里看一看
        clazz.getConstructor(type);
        return true;
    } catch (NoSuchMethodException e) {
        return false;
    }
}

// 将Wrapper三个类缓存到Protocol的ExtensionLoader中cachedWrapperClasses属性中。
private void cacheWrapperClass(Class<?> clazz) {
    if (cachedWrapperClasses == null) {
        cachedWrapperClasses = new ConcurrentHashSet<>();
    }
    cachedWrapperClasses.add(clazz);
}


```

> 上述过程分析了Wrapper三个类的加载过程。下面分析DubboProtocol的包装过程


> 在分析Protocol的篇幅中也有说过Protocol=Protocol$Adaptive这个动态适配器类，
  其实ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension()
  方法就是为了获取到这个动态生成的适配器类。此类中的export方法，就是前文中分析过的Invoker转
  Exporter过程中需要调用的方法。


```

public class Protocol$Adaptive implements Protocol {

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("The method public abstract void org.apache.dubbo.rpc.Protocol.destroy() of interface org.apache.dubbo.rpc.Protocol is not adaptive method!");
    }

    @Override
    public int getDefaultPort() {
        throw new UnsupportedOperationException("The method public abstract int org.apache.dubbo.rpc.Protocol.getDefaultPort() of interface org.apache.dubbo.rpc.Protocol is not adaptive method!");
    }

    public Invoker refer(Class class_, URL uRL) throws RpcException {
        String string;
        if (uRL == null) {
            throw new IllegalArgumentException("url == null");
        }
        URL uRL2 = uRL;
        String string2 = string = uRL2.getProtocol() == null ? "dubbo" : uRL2.getProtocol();
        if (string == null) {
            throw new IllegalStateException(new StringBuffer().append("Failed to get extension (org.apache.dubbo.rpc.Protocol) name from url (").append(uRL2.toString()).append(") use keys([protocol])").toString());
        }
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(string);
        return protocol.refer(class_, uRL);
    }


    public Exporter export(Invoker invoker) throws RpcException {
        String string;
        if (invoker == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.Invoker argument == null");
        }
        if (invoker.getUrl() == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.Invoker argument getUrl() == null");
        }
        URL uRL = invoker.getUrl();
        
        // 根据这里可以看出若是没有配置则是dubbo协议，不过我们在配置文件中配置的也是dubbo协议
        // debug可以看到本地暴露的uRL.getProtocol()="injvm"，
        // 远程暴露服务又分了两个情况，一个registry,一个是dubbo
        // 一般会走registry，即是向zk上注册的协议
        String string2 = string = uRL.getProtocol() == null ? "dubbo" : uRL.getProtocol();
        if (string == null) {
            throw new IllegalStateException(new StringBuffer().append("Failed to get extension (org.apache.dubbo.rpc.Protocol) name from url (").append(uRL.toString()).append(") use keys([protocol])").toString());
        }
        // 所以本地服务InjvmProtocol，远程注册的协议是RegistryProtocol
        // 接下来 我们就是来分析这个getExtension(string)方法，这个方法就是文中三个包装类的包装过程的入口
        // 这里的protocol其实是包装后的结果，
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(string);
        return protocol.export(invoker);
    }


}


```


> ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(string);由这个方法为入口进行分析，ExtensionLoader.getExtensionLoader(Protocol.class)方法其实在为ServiceConfig的protocol属性赋值的过程中，
  就已经缓存到map中了，且本文中的三个类也在getAdaptiveExtension()方法调用后进行了缓存。这里直接分析getExtension(string)方法
  
  
```

public T getExtension(String name) {
    if (StringUtils.isEmpty(name)) {
        throw new IllegalArgumentException("Extension name == null");
    }
    if ("true".equals(name)) {
        return getDefaultExtension();
    }
    Holder<Object> holder = getOrCreateHolder(name);
    Object instance = holder.get();
    if (instance == null) {
        synchronized (holder) {
            instance = holder.get();
            if (instance == null) {
                 // 进行创建
                instance = createExtension(name);
                holder.set(instance);
            }
        }
    }
    return (T) instance;
}


private T createExtension(String name) {
    Class<?> clazz = getExtensionClasses().get(name);
    if (clazz == null) {
        throw findException(name);
    }
    try {
        T instance = (T) EXTENSION_INSTANCES.get(clazz);
        if (instance == null) {
            EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
            instance = (T) EXTENSION_INSTANCES.get(clazz);
        }
        // 依赖注入，这里是对DubboProtocol进行依赖注入
        injectExtension(instance);
        // cachedWrapperClasses就是上述分析的三个Wrapper的缓存
        Set<Class<?>> wrapperClasses = cachedWrapperClasses;
        if (CollectionUtils.isNotEmpty(wrapperClasses)) {
            for (Class<?> wrapperClass : wrapperClasses) {
                // 这里就是对DubboProtocol进行包装的过程
                // 先是以instance为参数创建wrapperClass的实例，
                // 然后进行依赖注入
                instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
            }
        }
        // 最后返回包装后的结果
        // 也就是Protocol$Adaptive类里面获得的结果其实不是DubboProtocol，而是包装后的结果。
        return instance;
    } catch (Throwable t) {
        throw new IllegalStateException("Extension instance (name: " + name + ", class: " +
                type + ") couldn't be instantiated: " + t.getMessage(), t);
    }
}



```
  
 
> 三个的Wrapper的加载时机，包装时机都分析完毕了，其实本文还是对ExtensionLoader的扩展。主要完成过程还是ExtensionLoader类中的方法完成的






