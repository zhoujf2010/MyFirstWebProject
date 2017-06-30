package com.zjf.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2017-06-30T13:38:11.550+08:00
 * Generated source version: 3.1.6
 * 
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "WebService1Soap")
//@XmlSeeAlso({ObjectFactory.class})
public interface WebService1Soap {

    @WebMethod(operationName = "Add", action = "http://tempuri.org/Add")
    @RequestWrapper(localName = "Add", targetNamespace = "http://tempuri.org/", className = "org.tempuri.Add")
    @ResponseWrapper(localName = "AddResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.AddResponse")
    @WebResult(name = "AddResult", targetNamespace = "http://tempuri.org/")
    public int add(
        @WebParam(name = "x", targetNamespace = "http://tempuri.org/")
        int x,
        @WebParam(name = "y", targetNamespace = "http://tempuri.org/")
        int y
    );

    @WebMethod(operationName = "HelloWorld", action = "http://tempuri.org/HelloWorld")
    @RequestWrapper(localName = "HelloWorld", targetNamespace = "http://tempuri.org/", className = "org.tempuri.HelloWorld")
    @ResponseWrapper(localName = "HelloWorldResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.HelloWorldResponse")
    @WebResult(name = "HelloWorldResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String helloWorld();
}
