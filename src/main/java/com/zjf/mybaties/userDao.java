package com.zjf.mybaties;

import java.util.List;

public interface userDao
{
    public List<user> select(user bank);
    public List<user> selectcount();
    public int insert(user bank);
    public int update(user bank);
}
