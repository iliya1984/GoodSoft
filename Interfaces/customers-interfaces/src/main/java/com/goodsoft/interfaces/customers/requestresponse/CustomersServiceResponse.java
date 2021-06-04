package com.goodsoft.interfaces.customers.requestresponse;

import com.goodsoft.interfaces.customers.errorhandling.CustomersValidationError;
import java.util.Collection;

 public abstract class CustomersServiceResponse implements ICustomersServiceResponse {

    private  Collection<CustomersValidationError> _errors;

    @Override
    public Collection<CustomersValidationError> getValidationErrors() {
        return _errors;
    }

    @Override
    public void setValidationErrors(Collection<CustomersValidationError> errors) {
        _errors = errors;
    }
}
