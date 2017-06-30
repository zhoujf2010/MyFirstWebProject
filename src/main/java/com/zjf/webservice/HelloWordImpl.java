package com.zjf.webservice;

public class HelloWordImpl implements IHelloWorld
{

    @Override
    public String test(String str) {
        return "Hello " + str;
    }

}
