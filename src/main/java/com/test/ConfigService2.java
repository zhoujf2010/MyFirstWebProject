package com.test;

import org.springframework.stereotype.Component;

public class ConfigService2 implements IConfigService
{

    @Override
    public String getValue(String name) {
        return name+"_value222";
    }

}
