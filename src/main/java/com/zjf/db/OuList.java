package com.zjf.db;

import java.util.ArrayList;

public class OuList extends ArrayList
{
    
    public void add(OuInfo info){
        super.add(info);
    }
    
    public OuInfo get(int index){
        return (OuInfo)super.get(index);
    }

}
