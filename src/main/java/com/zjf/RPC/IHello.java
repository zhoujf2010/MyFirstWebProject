package com.zjf.RPC;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHello extends Remote
{

    public String calc(String str) throws RemoteException;
}
