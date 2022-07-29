package com.goodsoft.infra.mediator.errorhandling;


import br.com.fluentvalidator.context.Error;

import java.util.Collection;

public class ValidationException extends  Exception
{
    private Collection<Error> _errors;

    public ValidationException(String errorMessage, Collection<Error> errors)
    {
        super(errorMessage);

        _errors = errors;
    }

    public Collection<Error> getErrors()
    {
        return _errors;
    }
}
