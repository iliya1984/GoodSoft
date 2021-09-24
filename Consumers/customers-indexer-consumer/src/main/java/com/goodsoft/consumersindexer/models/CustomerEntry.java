package com.goodsoft.consumersindexer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerEntry
{
    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("lastName")
    public String lastName;
}
