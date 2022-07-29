package com.goodsoft.infra.gscore.configuration;

public class ConfigurationSection
{
    private  String _domainName;
    private  String _value;
    private  String _key;

    public String getKey(){ return  _key; }

    public void setKey(String value){ _key = value;}

    public String getDomainName()
    {
        return _domainName;
    }

    public void setDomainName(String value)
    {
        _domainName = value;
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
