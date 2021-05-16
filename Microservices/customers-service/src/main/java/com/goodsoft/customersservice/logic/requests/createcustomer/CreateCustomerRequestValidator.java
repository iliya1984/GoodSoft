package com.goodsoft.customersservice.logic.requests.createcustomer;

import br.com.fluentvalidator.AbstractValidator;
import com.goodsoft.customersservice.logic.annotations.CustomerRequestValidator;

@CustomerRequestValidator
public class CreateCustomerRequestValidator extends AbstractValidator<CreateCustomerRequest>
{
    @Override
    public void rules()
    {

    }
}
