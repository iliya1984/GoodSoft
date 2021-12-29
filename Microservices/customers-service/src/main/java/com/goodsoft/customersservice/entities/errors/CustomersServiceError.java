package com.goodsoft.customersservice.entities.errors;

import lombok.Getter;
import lombok.Setter;

public class CustomersServiceError
{
    @Getter
    @Setter
    private String errorCode;

    @Getter
    @Setter
    private String errorMessage;
}
