## Cluster

#### 1 简介

> 集群容错的策略



#### 2 类关系

> 

```

Cluster (com.alibaba.dubbo.rpc.cluster)
    AvailableCluster (org.apache.dubbo.rpc.cluster.support)
    FailfastCluster (org.apache.dubbo.rpc.cluster.support)
    FailsafeCluster (org.apache.dubbo.rpc.cluster.support)
    ForkingCluster (org.apache.dubbo.rpc.cluster.support)
    MergeableCluster (org.apache.dubbo.rpc.cluster.support)
    MockClusterWrapper (org.apache.dubbo.rpc.cluster.support.wrapper)
    BroadcastCluster (org.apache.dubbo.rpc.cluster.support)
    FailoverCluster (org.apache.dubbo.rpc.cluster.support) #失败重试，默认的集群容错策略
    FailbackCluster (org.apache.dubbo.rpc.cluster.support)
    RegistryAwareCluster (org.apache.dubbo.rpc.cluster.support)


```

#### 3 Cluster$Adaptive

> 适配器类

```

package org.apache.dubbo.rpc.cluster;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;

public class Cluster$Adaptive
implements Cluster {
    public Invoker join(Directory directory) throws RpcException {
        if (directory == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.cluster.Directory argument == null");
        }
        if (directory.getUrl() == null) {
            throw new IllegalArgumentException("org.apache.dubbo.rpc.cluster.Directory argument getUrl() == null");
        }
        URL uRL = directory.getUrl();
        // 获得集群容错的策略，默认是FailoverCluster
        String string = uRL.getParameter("cluster", "failover");
        if (string == null) {
            throw new IllegalStateException(new StringBuffer().append("Failed to get extension (org.apache.dubbo.rpc.cluster.Cluster) name from url (").append(uRL.toString()).append(") use keys([cluster])").toString());
        }

        Cluster cluster = ExtensionLoader.getExtensionLoader(Cluster.class).getExtension(string);
        return cluster.join(directory);
    }
}


```


#### 4 FailoverCluster

```

public class FailoverCluster implements Cluster {

    public final static String NAME = "failover";

    @Override
    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        // 直接创建了一个失败重试的策略
        return new FailoverClusterInvoker<T>(directory);
    }

}


```



#### 5 FailoverClusterInvoker


```

package org.apache.dubbo.rpc.cluster.support;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.support.RpcUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FailoverClusterInvoker<T> extends AbstractClusterInvoker<T> {

    private static final Logger logger = LoggerFactory.getLogger(FailoverClusterInvoker.class);

    public FailoverClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Result doInvoke(Invocation invocation, final List<Invoker<T>> invokers, LoadBalance loadbalance) throws RpcException {
        List<Invoker<T>> copyInvokers = invokers;
        checkInvokers(copyInvokers, invocation);
        String methodName = RpcUtils.getMethodName(invocation);
        int len = getUrl().getMethodParameter(methodName, Constants.RETRIES_KEY, Constants.DEFAULT_RETRIES) + 1;
        if (len <= 0) {
            len = 1;
        }
        // retry loop.
        RpcException le = null; // last exception.
        List<Invoker<T>> invoked = new ArrayList<Invoker<T>>(copyInvokers.size()); // invoked invokers.
        Set<String> providers = new HashSet<String>(len);
        // 
        for (int i = 0; i < len; i++) {
            // Reselect before retry to avoid a change of candidate `invokers`.
            // NOTE: if `invokers` changed, then `invoked` also lose accuracy.
            if (i > 0) {
                checkWhetherDestroyed();
                copyInvokers = list(invocation);
                // check again
                checkInvokers(copyInvokers, invocation);
            }
            Invoker<T> invoker = select(loadbalance, invocation, copyInvokers, invoked);
            invoked.add(invoker);
            RpcContext.getContext().setInvokers((List) invoked);
            try {
                Result result = invoker.invoke(invocation);
                if (le != null && logger.isWarnEnabled()) {
                    logger.warn("Although retry the method " + methodName
                            + " in the service " + getInterface().getName()
                            + " was successful by the provider " + invoker.getUrl().getAddress()
                            + ", but there have been failed providers " + providers
                            + " (" + providers.size() + "/" + copyInvokers.size()
                            + ") from the registry " + directory.getUrl().getAddress()
                            + " on the consumer " + NetUtils.getLocalHost()
                            + " using the dubbo version " + Version.getVersion() + ". Last error is: "
                            + le.getMessage(), le);
                }
                return result;
            } catch (RpcException e) {
                if (e.isBiz()) { // biz exception.
                    throw e;
                }
                le = e;
            } catch (Throwable e) {
                le = new RpcException(e.getMessage(), e);
            } finally {
                providers.add(invoker.getUrl().getAddress());
            }
        }
        throw new RpcException(le.getCode(), "Failed to invoke the method "
                + methodName + " in the service " + getInterface().getName()
                + ". Tried " + len + " times of the providers " + providers
                + " (" + providers.size() + "/" + copyInvokers.size()
                + ") from the registry " + directory.getUrl().getAddress()
                + " on the consumer " + NetUtils.getLocalHost() + " using the dubbo version "
                + Version.getVersion() + ". Last error is: "
                + le.getMessage(), le.getCause() != null ? le.getCause() : le);
    }

}



```
