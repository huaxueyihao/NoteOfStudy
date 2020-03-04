package org.apache.dubbo.demo.consumer;

import com.books.dubbo.demo.api.GrettingServiceAsync;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;

public class ApiConsumerForProviderAsync {


    public static void main(String[] args) throws InterruptedException {

        // 1.创建服务引用实例，并设置
        ReferenceConfig<GrettingServiceAsync> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface(GrettingServiceAsync.class);

        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        // 2.服务引用
        GrettingServiceAsync grettingService = referenceConfig.get();

        // 3.设置隐式参数
        RpcContext.getContext().setAttachment("company", "alibaba");

        // 4.获取future并设置回调
        CompletableFuture<String> future = grettingService.sayHello("world");

        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(v);
            }
        });

        Thread.currentThread().join();

    }


}
