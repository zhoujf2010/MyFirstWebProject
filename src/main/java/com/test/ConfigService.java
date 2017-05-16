package com.test;

import org.springframework.stereotype.Component;

@Component("configservice")
public class ConfigService implements IConfigService
{

    @Override
    public String getValue(String name) {
        return name+"_value";
    }

}
