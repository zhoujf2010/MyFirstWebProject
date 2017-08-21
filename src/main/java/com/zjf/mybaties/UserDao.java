package com.zjf.mybaties;

import java.util.List;

public interface UserDao
{
    public List<User> select(User user);
    public List<User> selectcount();
    public int insert(User bank);
    public int update(User bank);
    
    public User getUser(User id);
}
