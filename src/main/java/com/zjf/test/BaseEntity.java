package com.zjf.test;

/**
 * 实体基类，处理表相关的东西，比如分表，数据源选择  ...
 * 1、选择查询表
 * 2、选择查询数据源（jdbctemplate）
 * 3、读写分离处理
 * 
 * @author sxjun
 * @date 2014-7-7 下午3:47:25
 *
 */
public abstract class BaseEntity extends Record{

    /**
     * 
     */
    private static final long serialVersionUID = 6198276177585311750L;
}
