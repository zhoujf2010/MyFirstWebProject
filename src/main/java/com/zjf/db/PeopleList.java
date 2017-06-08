package com.zjf.db;

import java.util.ArrayList;

public class PeopleList extends ArrayList
{
    
    public void add(People p){
        super.add(p);
    }
    
    public People get(int index){
        return (People)super.get(index);
    }

}
