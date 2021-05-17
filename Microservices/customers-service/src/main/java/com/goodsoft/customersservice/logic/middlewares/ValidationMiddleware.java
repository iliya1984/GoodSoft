package com.goodsoft.customersservice.logic.middlewares;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipelinr;
import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.exception.ValidationException;
import com.goodsoft.customersservice.logic.Utils;
import com.goodsoft.customersservice.logic.annotations.CustomerRequestValidator;
import com.goodsoft.customersservice.logic.errorhandling.CustomerValidationException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class ValidationMiddleware<C> implements Command.Middleware
{
    @Autowired
    private ApplicationContext context;

    @SneakyThrows
    @Override
    public <R, C extends Command<R>> R invoke(C c, Next<R> next)
    {
        var requestClass = c.getClass();
        var requestName = requestClass.getSimpleName();
        var validatorName = Utils.firstCharToLowerCase(requestName + "Validator");

        var beanNames = context.getBeanNamesForAnnotation(CustomerRequestValidator.class);

        if(Arrays.stream(beanNames).anyMatch(bn -> bn.equals(validatorName)))
        {
            var requestValidator = (AbstractValidator<C>)context.getBean(validatorName);
            var validationResult = requestValidator.validate(c);

            if(false == validationResult.isValid())
            {
                String validationError = "Validation for" + requestName + " request failed";
                throw new CustomerValidationException(validationError);
            }
        }

        R response = next.invoke();
        return response;
    }
}
