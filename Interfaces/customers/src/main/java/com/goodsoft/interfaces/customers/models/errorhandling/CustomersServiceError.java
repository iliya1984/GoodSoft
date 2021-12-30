package com.goodsoft.interfaces.customers.models.errorhandling;

import lombok.Getter;
import lombok.Setter;

public class CustomersServiceError
{
    @Getter
    @Setter
    private String fieldName;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String message;
}
