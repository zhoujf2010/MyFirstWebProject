package com.zjf.db2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "People")
public class People implements java.io.Serializable
{
    private static final long serialVersionUID = -855076426421145308L;
    
    private int id;
    private String Name;
    private String Age;

    @Id
    @Column(name = "id", updatable = false, insertable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "Name")
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }

    @Column(name = "Age")
    public String getAge() {
        return Age;
    }
    public void setAge(String age) {
        Age = age;
    }
}