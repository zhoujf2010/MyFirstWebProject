package com.zjf.db3;

public class People extends Record
{
    public int getId() {
        return Integer.parseInt(super.get("id").toString());
    }

    public void setId(int id) {
        super.put("id", id);
    }

    public String getName() {
        return super.get("name").toString();
    }

    public void setName(String name) {
        super.put("name", name);
    }

    public String getAge() {
        return super.get("age").toString();
    }

    public void setAge(String age) {
        super.put("age", age);
    }

}
