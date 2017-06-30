package com.zjf.webservice;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class test
{

    public static void main(String[] args) {
        String url = "http://localhost/test/Webservice1.asmx?wsdl";

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress(url);
        factory.setServiceClass(WebService1Soap.class);
        WebService1Soap hw = (WebService1Soap)factory.create();
        
        System.out.println(hw.helloWorld());
        System.out.println(hw.add(2, 5));
    }

}
