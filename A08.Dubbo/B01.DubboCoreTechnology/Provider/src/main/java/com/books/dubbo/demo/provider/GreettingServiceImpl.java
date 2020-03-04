package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GreetingService;
import com.books.dubbo.demo.api.PoJo;
import com.books.dubbo.demo.api.Result;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.rpc.RpcContext;

import java.io.IOException;

public class GreettingServiceImpl implements GreetingService {

    public String sayHello(String name) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello " + name + " " + RpcContext.getContext().getAttachment("company");
    }

    public Result<String> testGeneric(PoJo pojo) {
        Result<String> result = new Result<String>();
        result.setSuccess(true);

        try {
            result.setData(JSON.json(pojo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
