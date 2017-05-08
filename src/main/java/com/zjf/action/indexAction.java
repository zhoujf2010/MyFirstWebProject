package com.zjf.action;

import java.util.Arrays;
import java.util.List;

public class indexAction
{
    public String fun1() {
        // return "{\"name\":\"Jeffrey\",\"age\":\"20\"}";

        String[] atp = {"Java", "C", "Python" };
        List<String> players = Arrays.asList(atp);
        // 以前的循环方式
        for (String player : players) {
            System.out.println(player);
        }

        // 使用 lambda 表达式以及函数操作(functional operation)
        players.forEach((player) -> System.out.println(player));

        // 在 Java 8 中使用双冒号操作符(double colon operator)
        players.forEach(System.out::println);

        return "";
    }

    // #region aaa
    public String getName() {
        return "";
    }

    // #endregion
}
