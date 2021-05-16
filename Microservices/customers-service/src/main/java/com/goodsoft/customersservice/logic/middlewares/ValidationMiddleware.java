package com.goodsoft.customersservice.logic.middlewares;

import an.awesome.pipelinr.Command;
import br.com.fluentvalidator.AbstractValidator;
import com.goodsoft.customersservice.logic.annotations.CustomerRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ValidationMiddleware<C> implements Command.Middleware
{
    @Autowired
    private ApplicationContext context;

    @Override
    public <R, C extends Command<R>> R invoke(C c, Next<R> next)
    {
        R response = next.invoke();
        return response;
    }
}
