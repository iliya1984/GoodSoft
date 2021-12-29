package com.goodsoft.customersservice.entities.search;

import lombok.Getter;
import lombok.Setter;

public class SortOptions
{
    @Getter
    @Setter
    private String propertyName;

    @Getter
    @Setter
    private SortOrder sortOrder;
}
