package com.thread.study.chapter08;

import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ActiveObjectProxy {

    private static class DispatchInvocationHandler implements InvocationHandler {
        private final Object delegate;
        private final ExecutorService scheduler;

        public DispatchInvocationHandler(Object delegate, ExecutorService scheduler) {
            this.delegate = delegate;
            this.scheduler = scheduler;
        }

        private String makeDelegateMethodName(final Method method, final Object[] arg) {
            String name = method.getName();
            name = "do" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            return name;
        }


        public static <T> T newInstance(Class<T> interf, Object servant, ExecutorService scheduler) {
            T f = (T) Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf}, new DispatchInvocationHandler(servant, scheduler));
            return f;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object returnValue = null;
            final Object delegate = this.delegate;
            final Method delegateMethod;
            // 如果拦截到的被调用方法是异步方法，则将其妆发到相应的doXXX方法
            if (Future.class.isAssignableFrom(method.getReturnType())) {
                delegateMethod = delegate.getClass().getMethod(makeDelegateMethodName(method, args), method.getParameterTypes());

                final ExecutorService scheduler = this.scheduler;
                Callable<Object> methodRequest = new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        Object rv = null;
                        try {
                            rv = delegateMethod.invoke(delegate, args);
                        } catch (IllegalArgumentException e) {
                            throw new Exception(e);
                        } catch (IllegalAccessException e) {
                            throw new Exception(e);
                        } catch (InvocationTargetException e) {
                            throw new Exception(e);
                        }
                        return rv;
                    }
                };
                Future<Object> future = scheduler.submit(methodRequest);
                returnValue = future;
            } else {
                // 若拦截到的方法调用不是IBU方法，则直接转发
                delegateMethod = delegate.getClass().getMethod(method.getName(), method.getParameterTypes());
                returnValue = delegateMethod.invoke(delegate, args);
            }
            return returnValue;
        }
    }

}
