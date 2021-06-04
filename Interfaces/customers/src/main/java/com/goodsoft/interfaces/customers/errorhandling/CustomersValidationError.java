package com.goodsoft.interfaces.customers.errorhandling;

import lombok.Getter;
import lombok.Setter;

public class CustomersValidationError
{
    @Getter
    @Setter
    private String errorMessage;

    @Getter
    @Setter
    private String errorCode;
}
