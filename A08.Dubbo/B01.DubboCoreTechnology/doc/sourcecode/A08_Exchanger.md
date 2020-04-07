### Exchanger

#### 1 简介

> 这个是在org.apache.dubbo.remoting.exchange包下面Exchanger：交换器/交换者，作用和其他服务器连接传输交互数据
  这个主要是远程dubbo暴露服务过程中需要启动的服务，就是从这个类的子类中开始的HeaderExchanger。

   

#### 2 类关系

```

@SPI(HeaderExchanger.NAME)
public interface Exchanger {

    /**
     * bind.
     *
     * @param url
     * @param handler
     * @return message server
     */
    @Adaptive({Constants.EXCHANGER_KEY})
    ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException;

    /**
     * connect.
     *
     * @param url
     * @param handler
     * @return message channel
     */
    @Adaptive({Constants.EXCHANGER_KEY})
    ExchangeClient connect(URL url, ExchangeHandler handler) throws RemotingException;

}


```


> 唯一的实现类：实现了两个方法，一个是作为客户端进行链接的方法，一个作为服务进行启动的方法

```

public class HeaderExchanger implements Exchanger {

    public static final String NAME = "header";

    @Override
    public ExchangeClient connect(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeClient(Transporters.connect(url, new DecodeHandler(new HeaderExchangeHandler(handler))), true);
    }

    @Override
    public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeServer(Transporters.bind(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
    }

}


```


#### 3 DubboProtocol#export方法进入


```

public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
    URL url = invoker.getUrl();

    // export service.
    String key = serviceKey(url);
    // 这里就转换完成了
    DubboExporter<T> exporter = new DubboExporter<T>(invoker, key, exporterMap);
    // 缓存
    exporterMap.put(key, exporter);

    //export an stub service for dispatching event
    Boolean isStubSupportEvent = url.getParameter(Constants.STUB_EVENT_KEY, Constants.DEFAULT_STUB_EVENT);
    Boolean isCallbackservice = url.getParameter(Constants.IS_CALLBACK_SERVICE, false);
    if (isStubSupportEvent && !isCallbackservice) {
        String stubServiceMethods = url.getParameter(Constants.STUB_EVENT_METHODS_KEY);
        if (stubServiceMethods == null || stubServiceMethods.length() == 0) {
            if (logger.isWarnEnabled()) {
                logger.warn(new IllegalStateException("consumer [" + url.getParameter(Constants.INTERFACE_KEY) +
                        "], has set stubproxy support event ,but no stub methods founded."));
            }

        } else {
            stubServiceMethodsMap.put(url.getServiceKey(), stubServiceMethods);
        }
    }

    // 这里是启动服务，这里后面再讲
    openServer(url);
    optimizeSerialization(url);

    return exporter;
}



private void openServer(URL url) {
        // find server.
        String key = url.getAddress();
        //client can export a service which's only for server to invoke
        boolean isServer = url.getParameter(Constants.IS_SERVER_KEY, true);
        if (isServer) {
            // 从缓存中获取服务
            ExchangeServer server = serverMap.get(key);
            if (server == null) {
                synchronized (this) {
                    server = serverMap.get(key);
                    if (server == null) {
                        // 缓存中没有，创建服务
                        serverMap.put(key, createServer(url));
                    }
                }
            } else {
                // server supports reset, use together with override
                server.reset(url);
            }
        }
    }

    private ExchangeServer createServer(URL url) {
        // 进行一些参数的处理
        url = URLBuilder.from(url)
                // send readonly event when server closes, it's enabled by default
                .addParameterIfAbsent(Constants.CHANNEL_READONLYEVENT_SENT_KEY, Boolean.TRUE.toString())
                // enable heartbeat by default
                .addParameterIfAbsent(Constants.HEARTBEAT_KEY, String.valueOf(Constants.DEFAULT_HEARTBEAT))
                .addParameter(Constants.CODEC_KEY, DubboCodec.NAME)
                .build();
        String str = url.getParameter(Constants.SERVER_KEY, Constants.DEFAULT_REMOTING_SERVER);

        if (str != null && str.length() > 0 && !ExtensionLoader.getExtensionLoader(Transporter.class).hasExtension(str)) {
            throw new RpcException("Unsupported server type: " + str + ", url: " + url);
        }

        ExchangeServer server;
        try {
            // 这里是绑定端口，启动服务的入口
            server = Exchangers.bind(url, requestHandler);
        } catch (RemotingException e) {
            throw new RpcException("Fail to start server(url: " + url + ") " + e.getMessage(), e);
        }

        str = url.getParameter(Constants.CLIENT_KEY);
        if (str != null && str.length() > 0) {
            Set<String> supportedTypes = ExtensionLoader.getExtensionLoader(Transporter.class).getSupportedExtensions();
            if (!supportedTypes.contains(str)) {
                throw new RpcException("Unsupported client type: " + str);
            }
        }

        return server;
    }



```



#### 4 Exchangers#bind

> Exchangers应该是个辅助的工具类。

```


public static ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
    if (url == null) {
        throw new IllegalArgumentException("url == null");
    }
    if (handler == null) {
        throw new IllegalArgumentException("handler == null");
    }
    // 追加参数
    url = url.addParameterIfAbsent(Constants.CODEC_KEY, "exchange");
    // 注意这里先获得Exchanger，然后调用了bind的方法
    // 后面可以知道这里返回的是HeaderExchanger的类型，然后调用HeaderExchanger#bind方法
    return getExchanger(url).bind(url, handler);
}


public static Exchanger getExchanger(URL url) {
    // 追加参数
    String type = url.getParameter(Constants.EXCHANGER_KEY, Constants.DEFAULT_EXCHANGER);
    return getExchanger(type);
}


public static Exchanger getExchanger(String type) {
    // debug这个type="header"，我们知道Exchanger的唯一子类是HeaderExchanger，
    // 显然这个返回的就是HeaderExchanger的类型
    return ExtensionLoader.getExtensionLoader(Exchanger.class).getExtension(type);
}


```



#### 4 HeaderExchanger#bind

> 这个方法返回了一个ExchangeServer服务

```

@Override
public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
    
    // DecodeHandler编/解码器
    // HeaderExchangeHandler自定义的channel的handler
    // Transporters辅助的工具类
    // 我们先看看Transporters的bind
    return new HeaderExchangeServer(Transporters.bind(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
}



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
    return getTransporter().bind(url, handler);
}



public static Transporter getTransporter() {
    return ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();
}



```
