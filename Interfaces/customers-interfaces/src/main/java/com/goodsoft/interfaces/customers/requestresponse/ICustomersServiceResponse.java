package com.goodsoft.interfaces.customers.requestresponse;

import com.goodsoft.interfaces.customers.errorhandling.CustomersValidationError;

import java.util.Collection;

public interface ICustomersServiceResponse
{
    Collection<CustomersValidationError> getValidationErrors();
    void setValidationErrors(Collection<CustomersValidationError> errors);
}
