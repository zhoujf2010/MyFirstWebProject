package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyHandler implements InvocationHandler
{
    private final IUserService original;

    public MyHandler(IUserService original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before call :" + method.getName());
        Object ret = method.invoke(original, args);
        System.out.println("after call :" + method.getName());
        return ret;
    }
}