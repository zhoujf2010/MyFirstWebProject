package com.test;

public class ConfigService implements IConfigService
{

    @Override
    public String getValue(String name) {
        return name+"_value";
    }

}
