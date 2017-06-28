package com.zjf.RPC;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class testclient
{

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        IHello rhello =(IHello) Naming.lookup("rmi://localhost:8888/test"); 
        
        System.out.println(rhello.calc("world"));
    }

}
