package com.test;

import java.util.HashMap;
import java.util.Map;

public class CommFactory
{
    public Map<String,Class> map = new HashMap<String,Class>();
    
    public CommFactory() {
        //读xml
        //Map
        map.put("userservice", UserServiceDB.class);
        
        //遍历所有类，观察是否头上有Name标记
    }

    public <T> T Create(String Name) {
//        if (Name.equals("userservice"))
//            return (T) new UserServiceDB();

        try {
            return (T)map.get(Name).newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
