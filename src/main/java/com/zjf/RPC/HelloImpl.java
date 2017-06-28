package com.zjf.RPC;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements IHello 
{
    protected HelloImpl() throws RemoteException {
        super();
    }

    public String calc(String str) throws RemoteException{
        System.out.println("我被调用了");
        return "Hello " + str;
    }
}
