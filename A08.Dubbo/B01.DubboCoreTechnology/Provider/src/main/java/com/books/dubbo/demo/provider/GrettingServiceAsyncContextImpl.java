package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GrettingServiceRpcContext;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GrettingServiceAsyncContextImpl implements GrettingServiceRpcContext {


    // 1.创建业务自定义线程池
    private final ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue<>(), new NamedThreadFactory("biz-thread-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy());


    // 2.创建服务处理接口，返回值为CompletableFuture
    @Override
    public String sayHello(String name) {

        // 2.1开启异步
        final AsyncContext asyncContext = RpcContext.startAsync();
        bizThreadPool.execute(() -> {
            // 2.2 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 2.3 写会响应
            asyncContext.write("Hello "+name+" "+RpcContext.getContext().getAttachment("company"));

        });

        return null;
    }
}
