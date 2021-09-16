package com.goodsoft.configurationservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.UUID;

public class DomainConfiguration
{
    @Getter
    @Setter
    private String domainName;

    @Getter
    @Setter
    private String value;
}
