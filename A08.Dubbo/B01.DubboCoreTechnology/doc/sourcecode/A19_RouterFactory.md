## RouterFactory

#### 1 简介

> 路由工厂


#### 2 类关系

```

RouterFactory
    CacheableRouterFactory (org.apache.dubbo.rpc.cluster)
        ServiceRouterFactory (org.apache.dubbo.rpc.cluster.router.condition.config)
        TagRouterFactory (org.apache.dubbo.rpc.cluster.router.tag)
    ConditionRouterFactory (org.apache.dubbo.rpc.cluster.router.condition)
    ScriptRouterFactory (org.apache.dubbo.rpc.cluster.router.script)
    MockRouterFactory (org.apache.dubbo.rpc.cluster.router.mock)
    RouterFactory (com.alibaba.dubbo.rpc.cluster)
    AppRouterFactory (org.apache.dubbo.rpc.cluster.router.condition.config)
    FileRouterFactory (org.apache.dubbo.rpc.cluster.router.file)


```



#### 3 RouterFactory

> 顶层接口

```

@SPI
public interface RouterFactory {

    /**
     * Create router.
     * Since 2.7.0, most of the time, we will not use @Adaptive feature, so it's kept only for compatibility.
     *
     * @param url url
     * @return router instance
     */
    @Adaptive("protocol")
    Router getRouter(URL url);
}


```



#### 4 CacheableRouterFactory

> 可缓存的路由工厂

```

public abstract class CacheableRouterFactory implements RouterFactory {
    private ConcurrentMap<String, Router> routerMap = new ConcurrentHashMap<>();

    @Override
    public Router getRouter(URL url) {
        routerMap.computeIfAbsent(url.getServiceKey(), k -> createRouter(url));
        return routerMap.get(url.getServiceKey());
    }

    protected abstract Router createRouter(URL url);
}


```


#### 5 TagRouterFactory

> 标签路由工厂

```

@Activate(order = 100)
public class TagRouterFactory extends CacheableRouterFactory {

    public static final String NAME = "tag";

    @Override
    protected Router createRouter(URL url) {
        return new TagRouter(DynamicConfiguration.getDynamicConfiguration(), url);
    }
}

```


#### 6 ServiceRouterFactory

> Service路由

```

@Activate(order = 300)
public class ServiceRouterFactory extends CacheableRouterFactory {

    public static final String NAME = "service";

    @Override
    protected Router createRouter(URL url) {
        return new ServiceRouter(DynamicConfiguration.getDynamicConfiguration(), url);
    }

}

```

#### 6 ServiceRouterFactory

> mock路由

```
@Activate
public class MockRouterFactory implements RouterFactory {
    public static final String NAME = "mock";

    @Override
    public Router getRouter(URL url) {
        return new MockInvokersSelector();
    }

}

```

#### 6 AppRouterFactory

```
@Activate(order = 200)
public class AppRouterFactory implements RouterFactory {
    public static final String NAME = "app";

    private volatile Router router;

    @Override
    public Router getRouter(URL url) {
        if (router != null) {
            return router;
        }
        synchronized (this) {
            if (router == null) {
                router = createRouter(url);
            }
        }
        return router;
    }

    private Router createRouter(URL url) {
        return new AppRouter(DynamicConfiguration.getDynamicConfiguration(), url);
    }
}

```
