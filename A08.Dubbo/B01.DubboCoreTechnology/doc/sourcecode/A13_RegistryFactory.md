### RegistryFactory

#### 1 简介

> 注册中心的工厂
  

#### 2 类关系

```

AbstractRegistryFactory (org.apache.dubbo.registry.support)
    MulticastRegistryFactory (org.apache.dubbo.registry.multicast)
    EtcdRegistryFactory (org.apache.dubbo.registry.etcd)
    ConsulRegistryFactory (org.apache.dubbo.registry.consul)
    ZookeeperRegistryFactory (org.apache.dubbo.registry.zookeeper)
    RedisRegistryFactory (org.apache.dubbo.registry.redis)
    DubboRegistryFactory (org.apache.dubbo.registry.dubbo)


```

#### 2 AbstractRegistryFactory#export

> 向zk注册服务的方法


```

public Registry getRegistry(URL url) {
    url = URLBuilder.from(url)
            .setPath(RegistryService.class.getName())
            .addParameter(Constants.INTERFACE_KEY, RegistryService.class.getName())
            .removeParameters(Constants.EXPORT_KEY, Constants.REFER_KEY)
            .build();
    String key = url.toServiceStringWithoutResolving();
    // Lock the registry access process to ensure a single instance of the registry
    LOCK.lock();
    try {
        Registry registry = REGISTRIES.get(key);
        if (registry != null) {
            return registry;
        }
        //create registry by spi/ioc
        registry = createRegistry(url);
        if (registry == null) {
            throw new IllegalStateException("Can not create registry " + url);
        }
        REGISTRIES.put(key, registry);
        return registry;
    } finally {
        // Release the lock
        LOCK.unlock();
    }
}


```





#### 3 ZookeeperRegistryFactory


```

public class ZookeeperRegistryFactory extends AbstractRegistryFactory {

    private ZookeeperTransporter zookeeperTransporter;

    /**
     * Invisible injection of zookeeper client via IOC/SPI
     * @param zookeeperTransporter
     */
    public void setZookeeperTransporter(ZookeeperTransporter zookeeperTransporter) {
        this.zookeeperTransporter = zookeeperTransporter;
    }

    @Override
    public Registry createRegistry(URL url) {
        return new ZookeeperRegistry(url, zookeeperTransporter);
    }

}




```
