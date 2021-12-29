package com.goodsoft.customersservice.entities.search;

import lombok.Getter;
import lombok.Setter;

public class SearchFilter
{
    @Getter
    @Setter
    private String propertyName;

    @Getter
    @Setter
    private FilterOperation operation;

    @Getter
    @Setter
    private String value;

}
