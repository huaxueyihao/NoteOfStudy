### Wrapper

#### 1 简介

> Service的服务包装类：这个是个抽象类，这个类的主要作用是动态生成自己的子类，每个子类都是一个Service的包装类
  先看下Wrapper类自己内部的一个实现类：所有的Wrapper子类，都会重写里面的方法。
  

```

private static final Wrapper OBJECT_WRAPPER = new Wrapper() {
    @Override
    public String[] getMethodNames() {
        return OBJECT_METHODS;
    }

    @Override
    public String[] getDeclaredMethodNames() {
        return OBJECT_METHODS;
    }

    @Override
    public String[] getPropertyNames() {
        return EMPTY_STRING_ARRAY;
    }

    @Override
    public Class<?> getPropertyType(String pn) {
        return null;
    }

    @Override
    public Object getPropertyValue(Object instance, String pn) throws NoSuchPropertyException {
        throw new NoSuchPropertyException("Property [" + pn + "] not found.");
    }

    @Override
    public void setPropertyValue(Object instance, String pn, Object pv) throws NoSuchPropertyException {
        throw new NoSuchPropertyException("Property [" + pn + "] not found.");
    }

    @Override
    public boolean hasProperty(String name) {
        return false;
    }

    // 这个方法就是JavassistProxyFactory#getInvoker里wrapper调用的方法，终于全部串起来了
    @Override
    public Object invokeMethod(Object instance, String mn, Class<?>[] types, Object[] args) throws NoSuchMethodException {
        if ("getClass".equals(mn)) {
            return instance.getClass();
        }
        if ("hashCode".equals(mn)) {
            return instance.hashCode();
        }
        if ("toString".equals(mn)) {
            return instance.toString();
        }
        if ("equals".equals(mn)) {
            if (args.length == 1) {
                return instance.equals(args[0]);
            }
            throw new IllegalArgumentException("Invoke method [" + mn + "] argument number error.");
        }
        throw new NoSuchMethodException("Method [" + mn + "] not found.");
    }
};


```


#### 2 Wrapper#getWrapper

> 

```

public static Wrapper getWrapper(Class<?> c) {
    // c是否是动态生成的类，如果是，while循环知道找到其父类不是动态生成的类
    while (ClassGenerator.isDynamicClass(c)) // can not wrapper on dynamic class.
    {
        c = c.getSuperclass();
    }

    if (c == Object.class) {
        return OBJECT_WRAPPER;
    }

    // WRAPPER_MAP是个缓存Wrapper的map
    Wrapper ret = WRAPPER_MAP.get(c);
    if (ret == null) {
        // 没有缓存就的去生成这个类了
        // 这里不研究这个生成方法，就是是反射，然后，拼接生成一个Wrapper类的字符串
        // 如下面的Wrapper1的就是通过此方法生成的，
        ret = makeWrapper(c);
        // 生成的类放入缓存中
        WRAPPER_MAP.put(c, ret);
    }
    return ret;
}


```

##### 2.1 GreettingService的包装类Wrapper1


```

/*
 * Decompiled with CFR.
 * 
 * Could not load the following classes:
 *  com.books.dubbo.demo.api.PoJo
 *  com.books.dubbo.demo.provider.GreettingServiceImpl
 */
package org.apache.dubbo.common.bytecode;

import com.books.dubbo.demo.api.PoJo;
import com.books.dubbo.demo.provider.GreettingServiceImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.dubbo.common.bytecode.ClassGenerator;
import org.apache.dubbo.common.bytecode.NoSuchMethodException;
import org.apache.dubbo.common.bytecode.NoSuchPropertyException;
import org.apache.dubbo.common.bytecode.Wrapper;

public class Wrapper1
extends Wrapper
implements ClassGenerator.DC {
    public static String[] pns;
    public static Map pts;
    // 方法名称
    public static String[] mns;
    // 私有方法
    public static String[] dmns;
    public static Class[] mts0;
    public static Class[] mts1;

    @Override
    public String[] getMethodNames() {
        return mns;
    }

    public Class getPropertyType(String string) {
        return (Class)pts.get(string);
    }

    
    @Override
    public String[] getDeclaredMethodNames() {
        return dmns;
    }

    @Override
    public String[] getPropertyNames() {
        return pns;
    }

    @Override
    public boolean hasProperty(String string) {
        return pts.containsKey(string);
    }

    @Override
    public Object getPropertyValue(Object object, String string) {
        try {
            GreettingServiceImpl greettingServiceImpl = (GreettingServiceImpl)object;
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        throw new NoSuchPropertyException(new StringBuffer().append("Not found property \"").append(string).append("\" field or setter method in class com.books.dubbo.demo.provider.GreettingServiceImpl.").toString());
    }

    @Override
    public void setPropertyValue(Object object, String string, Object object2) {
        try {
            GreettingServiceImpl greettingServiceImpl = (GreettingServiceImpl)object;
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        throw new NoSuchPropertyException(new StringBuffer().append("Not found property \"").append(string).append("\" field or setter method in class com.books.dubbo.demo.provider.GreettingServiceImpl.").toString());
    }

    // 这个方法最后调用的就是GreettingServiceImpl的方法
    public Object invokeMethod(Object object, String string, Class[] arrclass, Object[] arrobject) throws InvocationTargetException {
        GreettingServiceImpl greettingServiceImpl;
        try {
            greettingServiceImpl = (GreettingServiceImpl)object;
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        try {
            if ("testGeneric".equals(string) && arrclass.length == 1) {
                return greettingServiceImpl.testGeneric((PoJo)arrobject[0]);
            }
            if ("sayHello".equals(string) && arrclass.length == 1) {
                return greettingServiceImpl.sayHello((String)arrobject[0]);
            }
        }
        catch (Throwable throwable) {
            throw new InvocationTargetException(throwable);
        }
        throw new NoSuchMethodException(new StringBuffer().append("Not found method \"").append(string).append("\" in class com.books.dubbo.demo.provider.GreettingServiceImpl.").toString());
    }
}



```

> 到这里，就把URL对象如何转成Invoker对象串完了。
