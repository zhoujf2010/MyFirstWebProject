package com.zjf.db;

import java.util.ArrayList;

public class CommonList<T> extends ArrayList
{
    
//    public void add(T p){
//        super.add(p);
//    }
    
    public T get(int index){
        return (T)super.get(index);
    }

}
