package com.goodsoft.infra.microservice.validation;

import lombok.Getter;
import lombok.Setter;

public class ValidationError
{
    @Getter
    @Setter
    private String errorMessage;

    @Getter
    @Setter
    private String errorCode;
}
