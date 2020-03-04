package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

public class ApiGenericConsumerForBean {


    public static void main(String[] args) {

        // 1. 泛型参数固定为GenericService
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));

        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        // 2.设置为泛化引用，并且泛化类型为bean
        referenceConfig.setInterface("com.books.dubbo.demo.api.GreetingService");
        referenceConfig.setGeneric("bean");

        // 3.用org.apache.dubbo.rpc.service.GenericService替代所有接口引用
        GenericService genericService = referenceConfig.get();

        // 4.泛型调用，参数使用JavaBean进行序列化
        JavaBeanDescriptor param = JavaBeanSerializeUtil.serialize("world");
        Object result = genericService.$invoke("sayHello",new String[]{"java.lang.String"},new Object[]{param});


        // 5.结果反序列化
        result = JavaBeanSerializeUtil.serialize((JavaBeanDescriptor) result);
        System.out.println(result);

    }


}
