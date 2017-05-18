package com.zjf.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("indexAction")
@Scope("request")
public class indexAction
{
    public String fun1() {
         return "{\"name\":\"Jeffrey\",\"age\":\"20\"}";

      
    }

    // #region aaa
    public String getName() {
        return "";
    }

    // #endregion
}
