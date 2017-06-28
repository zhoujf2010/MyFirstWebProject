package com.zjf.RPC;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class testSerice
{

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
        LocateRegistry.createRegistry(8888); 
        IHello hello = new HelloImpl();
        Naming.bind("rmi://localhost:8888/test",hello); 
        System.out.println("服务启动成功!");    }

}
