package com.goodsoft.customersservice.configuration;

public class ConfigurationDto
{
    private  String _name;
    private  String _value;

    public String getName()
    {
        return _name;
    }

    public void setName(String value)
    {
        _name = value;
    }

    public String getValue()
    {
        return _value;
    }

    public void setValue(String value)
    {
        _value = value;
    }
}
