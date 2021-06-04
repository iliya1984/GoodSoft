package com.goodsoft.infra.mediator.middlewares;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipelinr;
import br.com.fluentvalidator.AbstractValidator;
import com.goodsoft.infra.mediator.annotations.RequestValidator;
import com.goodsoft.infra.mediator.errorhandling.ValidationException;
import com.goodsoft.infra.mediator.utils.Utils;
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

        var beanNames = context.getBeanNamesForAnnotation(RequestValidator.class);

        if(Arrays.stream(beanNames).anyMatch(bn -> bn.equals(validatorName)))
        {
            var requestValidator = (AbstractValidator<C>)context.getBean(validatorName);
            var validationResult = requestValidator.validate(c);

            if(false == validationResult.isValid())
            {
                String validationError = "Validation for" + requestName + " request failed";
                throw new ValidationException(validationError, validationResult.getErrors());
            }
        }

        R response = next.invoke();
        return response;
    }
}

