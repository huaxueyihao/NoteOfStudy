package org.apache.dubbo.demo.consumer;

import com.books.dubbo.demo.api.GreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.remoting.exchange.ResponseCallback;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.dubbo.FutureAdapter;

public class ApiAsyncConsumerForCallBack {


    public static void main(String[] args) throws InterruptedException {

        // 1.创建引用实例
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface(GreetingService.class);

        referenceConfig.setTimeout(5000);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        // 2. 设置异步
        referenceConfig.setAsync(true);

        // 3. 直接返回null
        GreetingService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello("world"));

        // 4. 异步执行回调

        ((FutureAdapter) RpcContext.getContext().getFuture()).getFuture().setCallback(new ResponseCallback() {
            @Override
            public void done(Object response) {
                System.out.println("result:" + response);
            }

            @Override
            public void caught(Throwable exception) {
                System.out.println("error:" + exception.getLocalizedMessage());
            }
        });

        Thread.currentThread().join();

    }


}
