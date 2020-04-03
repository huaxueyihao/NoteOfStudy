### ExtensionLoader

#### 1 简介

> 名称是扩展加载器：也就是加载dubbo扩展的。dubbo是基于spi机制根据不同的策略动态加载一些扩展类，同时会生成一些适配器类。
  这个类无其他扩展子类，且类中代码较多。

#### 2 ServiceConfig的属性protocol、proxyFactory

> 这两个属性都是通过ExtensionLoader加载的


```

/**
 * The {@link Protocol} implementation with adaptive functionality,it will be different in different scenarios.
 * A particular {@link Protocol} implementation is determined by the protocol attribute in the {@link URL}.
 * For example:
 *
 * <li>when the url is registry://224.5.6.7:1234/org.apache.dubbo.registry.RegistryService?application=dubbo-sample,
 * then the protocol is <b>RegistryProtocol</b></li>
 *
 * <li>when the url is dubbo://224.5.6.7:1234/org.apache.dubbo.config.api.DemoService?application=dubbo-sample, then
 * the protocol is <b>DubboProtocol</b></li>
 * <p>
 * Actually，when the {@link ExtensionLoader} init the {@link Protocol} instants,it will automatically wraps two
 * layers, and eventually will get a <b>ProtocolFilterWrapper</b> or <b>ProtocolListenerWrapper</b>
 */
private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

/**
 * A {@link ProxyFactory} implementation that will generate a exported service proxy,the JavassistProxyFactory is its
 * default implementation
 */
private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();


```



#### 3 ExtensionLoader#getExtensionLoader

```

public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
    if (type == null) {
        throw new IllegalArgumentException("Extension type == null");
    }
    // type必须是接口
    if (!type.isInterface()) {
        throw new IllegalArgumentException("Extension type (" + type + ") is not an interface!");
    }
    
    // 且必须是有SPI注解的
    if (!withExtensionAnnotation(type)) {
        throw new IllegalArgumentException("Extension type (" + type +
                ") is not an extension, because it is NOT annotated with @" + SPI.class.getSimpleName() + "!");
    }

    // EXTENSION_LOADERS缓存的map，先从缓存中获取，若是没有，则直接创建
    ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
    if (loader == null) {
        
        EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
        loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
    }
    return loader;
}


private static <T> boolean withExtensionAnnotation(Class<T> type) {
    return type.isAnnotationPresent(SPI.class);
}

// 构造器
private ExtensionLoader(Class<?> type) {
    this.type = type;
    // ExtensionFactory扩展工厂
    // 我们以ServiceConfig的属性protocol为例，这里执行三目运算符后面的代码，需要加载ExtensionFactory的扩展类赋值给objectFactory
    // 那这里ExtensionLoader.getExtensionLoader(ExtensionFactory.class)调用后又会走到这里，不过再次走到这里的时候，三目运行结果是null
    // 也就是EXTENSION_LOADERS缓存中缓存的第一个类型是ExtensionFactory类型的类
    // 之后会执行ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension()的getAdaptiveExtension()部分
    // 分析完加载ExtensionFactory的方法后，可以知道getAdaptiveExtension返会的是AdaptiveExtensionFactory子类，即objectFactory=AdaptiveExtensionFactory
    
    // 此时不要忘了，我们最初始由加载是为了ServiceConfig的属性protocol字段而来，到这里只能是
    // Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();代码中的ExtensionLoader.getExtensionLoader(Protocol.class)部分执行完了
    // 也就是ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension()代码执行完，代表ExtensionLoader.getExtensionLoader(Protocol.class)这部分代码基本执行完了
    
    // 同样ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension()的getAdaptiveExtension还需要执行，这个时候又要想分析ExtensionLoader的套路一样来一遍了进行Protocol子类的加载
    // 以及生成Protocol$Adaptive的适配器类
    objectFactory = (type == ExtensionFactory.class ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
}


```

  
  
##### 3.1 ExtensionLoader#getAdaptiveExtension

> 获得适配的扩展类



```

public T getAdaptiveExtension() {
    // private final Holder<Object> cachedAdaptiveInstance = new Holder<>();
    // 这个先尝试从属性容器中获得
    Object instance = cachedAdaptiveInstance.get();
    if (instance == null) {
        if (createAdaptiveInstanceError == null) {
            synchronized (cachedAdaptiveInstance) {
                instance = cachedAdaptiveInstance.get();
                if (instance == null) {
                    try {
                        // 没有，则进行创建
                        instance = createAdaptiveExtension();
                        // 放入缓存容器中
                        cachedAdaptiveInstance.set(instance);
                    } catch (Throwable t) {
                        createAdaptiveInstanceError = t;
                        throw new IllegalStateException("Failed to create adaptive instance: " + t.toString(), t);
                    }
                }
            }
        } else {
            throw new IllegalStateException("Failed to create adaptive instance: " + createAdaptiveInstanceError.toString(), createAdaptiveInstanceError);
        }
    }

    return (T) instance;
}



private T createAdaptiveExtension() {
        try {
        // 这里injectExtension是完成自动依赖注入等工作的方法
        // getAdaptiveExtensionClass是获得适配器的扩展类
        // 目前我们还是在为了获取ExtensionFactory的扩展类
        // 先看getAdaptiveExtensionClass方法
        // 后看injectExtension方法
        return injectExtension((T) getAdaptiveExtensionClass().newInstance());
    } catch (Exception e) {
        throw new IllegalStateException("Can't create adaptive extension " + type + ", cause: " + e.getMessage(), e);
    }
}



private Class<?> getAdaptiveExtensionClass() {
    // 这个方法是根据spi机制进行扩展子类的动态加载
    // 比如ExtensionFactory的子类有：SpiExtensionFactory、SpringExtensionFactory、AdaptiveExtensionFactory
    getExtensionClasses();
    // 在分析上个方法的时候，在最后加载完子类之后，有对凡是类上有Adaptive注解的都进行缓存到cachedAdaptiveClass中
    // 我们看到加载ExtensionFactory子类中，AdaptiveExtensionFactory有@Adaptive注解，所以加载ExtensionFactory时AdaptiveExtensionFactory被赋给cachedAdaptiveClass
    if (cachedAdaptiveClass != null) {
        return cachedAdaptiveClass;
    }
    // 如果走到这里这是进行生成
    return cachedAdaptiveClass = createAdaptiveExtensionClass();
}





```


##### 3.2 ExtensionLoader#getExtensionClasses


````

private Map<String, Class<?>> getExtensionClasses() {
    // private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    // 和cachedAdaptiveInstance属性一样，是个缓存容器的属性
    Map<String, Class<?>> classes = cachedClasses.get();
    if (classes == null) {
        synchronized (cachedClasses) {
            classes = cachedClasses.get();
            if (classes == null) {
                // 这个方法开始加载
                classes = loadExtensionClasses();
                cachedClasses.set(classes);
            }
        }
    }
    return classes;
}



private Map<String, Class<?>> loadExtensionClasses() {
        cacheDefaultExtensionName();

    // 创建一个map
    Map<String, Class<?>> extensionClasses = new HashMap<>();
    // SERVICES_DIRECTORY = "META-INF/services/";
    // DUBBO_DIRECTORY = "META-INF/dubbo/";
    // DUBBO_INTERNAL_DIRECTORY = DUBBO_DIRECTORY + "internal/";
    // 加载在DUBBO_INTERNAL_DIRECTORY、DUBBO_DIRECTORY、SERVICES_DIRECTORY目录下文件中配置的类
    // 可以在dubbo的jar文件中可以看到目录META-INF/dubbo/internal,在当前目录下可以看到有个org.apache.dubbo.common.extension.factory.ExtensionFactory文件
    // 文件的内容如下，正是ExtensionFactory的三个子类
    // adaptive=org.apache.dubbo.common.extension.factory.AdaptiveExtensionFactory
    // spi=org.apache.dubbo.common.extension.factory.SpiExtensionFactory
    // spring=org.apache.dubbo.config.spring.extension.SpringExtensionFactory
    // 也就是loadDirectory方法将AdaptiveExtensionFactory、SpiExtensionFactory、SpringExtensionFactory动态加载进来了，并且缓存了起来
    
    loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY, type.getName());
    loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
    loadDirectory(extensionClasses, DUBBO_DIRECTORY, type.getName());
    loadDirectory(extensionClasses, DUBBO_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
    loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName());
    loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
    return extensionClasses;
}



private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir, String type) {
    String fileName = dir + type;
    try {
        Enumeration<java.net.URL> urls;
        // 获得类加载器
        ClassLoader classLoader = findClassLoader();
        // 这里是为了加载目录下的文件
        if (classLoader != null) {
            urls = classLoader.getResources(fileName);
        } else {
            urls = ClassLoader.getSystemResources(fileName);
        }
        if (urls != null) {
            while (urls.hasMoreElements()) {
                java.net.URL resourceURL = urls.nextElement();
                // 这个方法，是将那些扩展子类动态加载进来，并且缓存到extensionClasses的中
                loadResource(extensionClasses, classLoader, resourceURL);
            }
        }
    } catch (Throwable t) {
        logger.error("Exception occurred when loading extension class (interface: " +
                type + ", description file: " + fileName + ").", t);
    }
}



private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, java.net.URL resourceURL) {
    try {
        // IO流读取文件内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8))) {
            String line;
            // 循环简单解析后直接进行加载
            while ((line = reader.readLine()) != null) {
                final int ci = line.indexOf('#');
                if (ci >= 0) {
                    line = line.substring(0, ci);
                }
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        String name = null;
                        int i = line.indexOf('=');
                        if (i > 0) {
                            name = line.substring(0, i).trim();
                            line = line.substring(i + 1).trim();
                        }
                        if (line.length() > 0) {
                            // 进行加载
                            loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name);
                        }
                    } catch (Throwable t) {
                        IllegalStateException e = new IllegalStateException("Failed to load extension class (interface: " + type + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
                        exceptions.put(line, e);
                    }
                }
            }
        }
    } catch (Throwable t) {
        logger.error("Exception occurred when loading extension class (interface: " +
                type + ", class file: " + resourceURL + ") in " + resourceURL, t);
    }
}



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

private void cacheAdaptiveClass(Class<?> clazz) {
    if (cachedAdaptiveClass == null) {
        cachedAdaptiveClass = clazz;
    } else if (!cachedAdaptiveClass.equals(clazz)) {
        throw new IllegalStateException("More than 1 adaptive class found: "
                + cachedAdaptiveClass.getClass().getName()
                + ", " + clazz.getClass().getName());
    }
}

private void saveInExtensionClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name) {
    Class<?> c = extensionClasses.get(name);
    if (c == null) {
        extensionClasses.put(name, clazz);
    } else if (c != clazz) {
        throw new IllegalStateException("Duplicate extension " + type.getName() + " name " + name + " on " + c.getName() + " and " + clazz.getName());
    }
}


````



##### 3.2 ExtensionLoader#createAdaptiveExtensionClass

```

private Class<?> createAdaptiveExtensionClass() {
    // 这有个适配器扩展类生成器，生成对应的字符串，然后用类加载器加载，使用compiler编译器进行编译
    // 这里不在继续深挖了
    String code = new AdaptiveClassCodeGenerator(type, cachedDefaultName).generate();
    ClassLoader classLoader = findClassLoader();
    org.apache.dubbo.common.compiler.Compiler compiler = ExtensionLoader.getExtensionLoader(org.apache.dubbo.common.compiler.Compiler.class).getAdaptiveExtension();
    return compiler.compile(code, classLoader);
}



```

##### 3.4 ExtensionLoader#injectExtension
> 这里完成的是依赖注入的问题，就是针对getAdaptiveExtensionClass方法生成的扩展适配器类进行依赖注入。
  

```

private T injectExtension(T instance) {
    try {
        // 显然当加载ExtensionFactory时，objectFactory==null,也就是ExtensionFactory是不需要依赖注入的
        // 也就是ExtensionFactory的加载完毕了
        // 
        if (objectFactory != null) {
            for (Method method : instance.getClass().getMethods()) {
                if (isSetter(method)) {                    /**

                     * Check {@link DisableInject} to see if we need auto injection for this property
                     */
                    if (method.getAnnotation(DisableInject.class) != null) {
                        continue;
                    }
                    Class<?> pt = method.getParameterTypes()[0];
                    if (ReflectUtils.isPrimitives(pt)) {
                        continue;
                    }
                    try {
                        String property = getSetterProperty(method);
                        Object object = objectFactory.getExtension(pt, property);
                        if (object != null) {
                            method.invoke(instance, object);
                        }
                    } catch (Exception e) {
                        logger.error("Failed to inject via method " + method.getName()
                                + " of interface " + type.getName() + ": " + e.getMessage(), e);
                    }
                }
            }
        }
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    }
    return instance;
}




```
