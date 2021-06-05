package com.goodsoft.configurationservice.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class ConfigurationSection
{
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String value;
}
