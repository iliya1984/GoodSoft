package com.goodsoft.customersservice.logic.errorhandling;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;

public class CustomerValidationException extends  Exception
{
   public CustomerValidationException(String errorMessage)
   {
       super(errorMessage);
   }
}
