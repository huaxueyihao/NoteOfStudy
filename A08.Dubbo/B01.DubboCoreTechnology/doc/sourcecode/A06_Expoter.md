### Exporter

#### 1 简介

> 提供者的真正的向外暴露服务的对象


```

public interface Exporter<T> {

    /**
     * get invoker.
     *
     * @return invoker
     */
    // 获得Invoker，这个应该是从Exporter获得Invoker的
    Invoker<T> getInvoker();

    /**
     * unexport.
     * <p>
     * <code>
     * getInvoker().destroy();
     * </code>
     */
    void unexport();

}




```




#### 2 类关系图

> 这里主要介绍AbstractExporter、InjvmExporter、DubboExporter

```

AbstractExporter (org.apache.dubbo.rpc.protocol)
    1 in AbstractProxyProtocol (org.apache.dubbo.rpc.protocol)
    InjvmExporter (org.apache.dubbo.rpc.protocol.injvm) #这个是本地服务暴露的Exporter
    DubboExporter (org.apache.dubbo.rpc.protocol.dubbo) #这个是远程服务暴露的Exporter
DestroyableExporter in RegistryProtocol (org.apache.dubbo.registry.integration)
ExporterChangeableWrapper in RegistryProtocol (org.apache.dubbo.registry.integration)
Exporter (com.alibaba.dubbo.rpc)
    CompatibleExporter in Exporter (com.alibaba.dubbo.rpc)
ListenerExporterWrapper (org.apache.dubbo.rpc.listener)


```


#### 3 AbstractExporter

> 类比较简单

```

public abstract class AbstractExporter<T> implements Exporter<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Invoker<T> invoker;

    private volatile boolean unexported = false;

    public AbstractExporter(Invoker<T> invoker) {
        if (invoker == null) {
            throw new IllegalStateException("service invoker == null");
        }
        if (invoker.getInterface() == null) {
            throw new IllegalStateException("service type == null");
        }
        if (invoker.getUrl() == null) {
            throw new IllegalStateException("service url == null");
        }
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }

    @Override
    public void unexport() {
        if (unexported) {
            return;
        }
        unexported = true;
        getInvoker().destroy();
    }

    @Override
    public String toString() {
        return getInvoker().toString();
    }

}



```




#### 4 InjvmExporter

```

class InjvmExporter<T> extends AbstractExporter<T> {

    private final String key;

    private final Map<String, Exporter<?>> exporterMap;

    InjvmExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
        //exporterMap是上级传过来的这里做了个缓存操作
        exporterMap.put(key, this);
    }

    @Override
    public void unexport() {
        super.unexport();
        exporterMap.remove(key);
    }

}




```


#### 5 DubboExporter

> duboo协议：远程暴露服务生成的Exporter


```

public class DubboExporter<T> extends AbstractExporter<T> {

    private final String key;

    private final Map<String, Exporter<?>> exporterMap;

    public DubboExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
        // 这里没有缓存的操作，缓存操作在DubboProtocol中
    }

    @Override
    public void unexport() {
        super.unexport();
        exporterMap.remove(key);
    }

}




```






















