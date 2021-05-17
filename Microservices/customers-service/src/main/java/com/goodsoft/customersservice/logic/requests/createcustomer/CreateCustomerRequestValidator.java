package com.goodsoft.customersservice.logic.requests.createcustomer;

import br.com.fluentvalidator.AbstractValidator;
import com.goodsoft.customersservice.logic.annotations.CustomerRequestValidator;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.function.Predicate;

@CustomerRequestValidator
public class CreateCustomerRequestValidator extends AbstractValidator<CreateCustomerRequest>
{
    @Override
    public void rules()
    {
        ruleFor(e -> e.getModel())
               .must(Objects::nonNull);

        ruleFor(e -> e.getModel().getFirstName())
                .must(Strings::isNotEmpty)
                .withMessage("Customer first name validation error");
    }
}
