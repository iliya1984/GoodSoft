package com.goodsoft.infra.microservice.validation;

import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ValidationFailure
{
    @Getter
    @Setter
    private String fieldName;

    @Getter
    @Setter
    private List<ValidationError> errors;

    public ValidationFailure()
    {
        errors  = new ArrayList();
    }
}
