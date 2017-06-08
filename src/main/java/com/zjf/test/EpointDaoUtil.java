package com.zjf.test;

public class EpointDaoUtil
{

    /**
     * 判断是否为BaseEntity基类
     * 
     * @param clazz
     * @return
     */
    public static boolean isBaseEntity(Class<?> clazz) {
        boolean findit = false;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (clazz.getName().equals(BaseEntity.class.getName())) {
                findit = true;
                break;
            }
        }
        return findit;
    }

    /**
     * 判断是否为Record基类
     * 
     * @param clazz
     * @return
     */
    public static boolean isRecord(Class<?> clazz) {
        boolean findit = false;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (clazz.getName().equals(Record.class.getName())) {
                findit = true;
                break;
            }
        }
        return findit;
    }

    /**
     * 字段大小写判断
     * 
     * @param key
     * @return
     */
    public static String caps(String key) {
        /*
         * if (EntityScanner.getCaps() == Caps.LOWER) return key.toLowerCase();
         * else if (EntityScanner.getCaps() == Caps.SENSITIVE) return key; else
         * if (EntityScanner.getCaps() == Caps.UPPER) return key.toUpperCase();
         * else return key.toLowerCase();
         */
        // 暂时没必要用到这些判断
        return key.toLowerCase();
    }
}
