### Transporter

#### 1 简介

> 名称：传输器/传输者，和Exchanger是对应的，里面也是两个方法一个bind，一个connect。


```

@SPI("netty")
public interface Transporter {

    /**
     * Bind a server.
     *
     * @param url     server url
     * @param handler
     * @return server
     * @throws RemotingException
     * @see org.apache.dubbo.remoting.Transporters#bind(URL, ChannelHandler...)
     */
    @Adaptive({Constants.SERVER_KEY, Constants.TRANSPORTER_KEY})
    Server bind(URL url, ChannelHandler handler) throws RemotingException;

    /**
     * Connect to a server.
     *
     * @param url     server url
     * @param handler
     * @return client
     * @throws RemotingException
     * @see org.apache.dubbo.remoting.Transporters#connect(URL, ChannelHandler...)
     */
    @Adaptive({Constants.CLIENT_KEY, Constants.TRANSPORTER_KEY})
    Client connect(URL url, ChannelHandler handler) throws RemotingException;

}



```

#### 2 类关系

> 以为几个子类，不同的传入者,其中默认使用的NettyTransporter(netty4包下的)。


```

NettyTransporter (org.apache.dubbo.remoting.transport.netty4) 
GrizzlyTransporter (org.apache.dubbo.remoting.transport.grizzly)
NettyTransporter (org.apache.dubbo.remoting.transport.netty)
Transporter (com.alibaba.dubbo.remoting)
MinaTransporter (org.apache.dubbo.remoting.transport.mina)


```



#### 3 Transporters#bind

> 上一篇，代码跟踪到这里

```

// Transporters#bind
public static Server bind(URL url, ChannelHandler... handlers) throws RemotingException {
    if (url == null) {
        throw new IllegalArgumentException("url == null");
    }
    if (handlers == null || handlers.length == 0) {
        throw new IllegalArgumentException("handlers == null");
    }
    ChannelHandler handler;
    if (handlers.length == 1) {
        handler = handlers[0];
    } else {
        handler = new ChannelHandlerDispatcher(handlers);
    }
    // 获得Transporter，并调用bind方法
    // debug可以看到这里返回的是netty4包下的NettyTransporter
    return getTransporter().bind(url, handler);
}


public static Transporter getTransporter() {
    return ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();
}


```

  
  
##### 3.1 NettyTransporter#bind

> netty传输器



```

public class NettyTransporter implements Transporter {

    public static final String NAME = "netty";

    @Override
    public Server bind(URL url, ChannelHandler listener) throws RemotingException {
        // 创建netty服务器
        return new NettyServer(url, listener);
    }

    @Override
    public Client connect(URL url, ChannelHandler listener) throws RemotingException {
        // 创建netty客户端
        return new NettyClient(url, listener);
    }

}

```

