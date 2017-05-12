package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CommFactory
{
    public Map<String, Class> map = new HashMap<String, Class>();

    public CommFactory() {
        // 读xml
        // Map
        map.put("userservice", UserServiceDB.class);
        map.put("configService", ConfigService.class);

        // 遍历所有类，观察是否头上有Name标记
    }

    public <T> T Create(String Name) {
        // if (Name.equals("userservice"))
        // return (T) new UserServiceDB();

        try {
            Object val = map.get(Name).newInstance();

            for (Field fd : map.get(Name).getDeclaredFields()) {
                Object ann = fd.getAnnotation(MyAutowired.class);
                if (ann != null) {
                    Object fdval = Create(fd.getName());
                    fd.setAccessible(true);
                    fd.set(val, fdval);
                }
            }

            
//            //Handle示例
//            val = Proxy.newProxyInstance(CommFactory.class.getClassLoader(), new Class[] {map.get(Name) },
//                    new MyHandler(val));
            
            return (T) val;
        }
        catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
