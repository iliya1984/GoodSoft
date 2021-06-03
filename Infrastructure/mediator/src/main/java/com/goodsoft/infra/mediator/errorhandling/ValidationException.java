package com.goodsoft.infra.mediator.errorhandling;


public class ValidationException extends  Exception
{
    public ValidationException(String errorMessage)
    {
        super(errorMessage);
    }
}
