package com.goodsoft.infra.microservice.models;

import com.goodsoft.infra.microservice.validation.ValidationFailure;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

public class UnprocessableEntityModel
{
    @Getter
    @Setter
    private Collection<ValidationFailure> failureList;
}
