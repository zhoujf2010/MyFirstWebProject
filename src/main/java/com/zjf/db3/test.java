package com.zjf.db3;

import java.util.List;

public class test
{

    public static void main(String[] args) {

        List<People> lst = CommonDao.getInstance().findList("select * from people", People.class);
        for (People item : lst) {
            System.out.println(item.getId() + "  " + item.getName() + "  " + item.getAge());
            
            item.get("address");
        }

    }

}
