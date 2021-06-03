package com.goodsoft.customersservice.logic.createcustomer;

import br.com.fluentvalidator.AbstractValidator;
import com.goodsoft.infra.mediator.annotations.RequestValidator;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

@RequestValidator
public class CreateCustomerCommandValidator extends AbstractValidator<CreateCustomerCommand>
{
    @Override
    public void rules()
    {
        ruleFor(e -> e.getRequest())
               .must(Objects::nonNull);

        ruleFor(e -> e.getRequest().getFirstName())
                .must(Strings::isNotEmpty)
                .withMessage("Customer first name validation error");
    }
}
