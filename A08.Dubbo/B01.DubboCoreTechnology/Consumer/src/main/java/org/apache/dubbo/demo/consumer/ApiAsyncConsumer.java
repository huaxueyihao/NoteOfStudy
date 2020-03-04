package org.apache.dubbo.demo.consumer;

import com.books.dubbo.demo.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ApiAsyncConsumer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1.创建引用实例，并设置属性
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<GreetingService>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        // 2.设置为异步
        referenceConfig.setAsync(true);
        // 3.直接返回null
        GreetingService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello("world"));

        // 4.异步执行回调
        Future<String> future = RpcContext.getContext().getFuture();
        System.out.println(future.get());


    }

}
