package com.goodsoft.customersservice.logic.middlewares;

import an.awesome.pipelinr.Command;
import br.com.fluentvalidator.AbstractValidator;
import org.apache.catalina.core.ApplicationContext;

public class ValidationMiddleware<C> implements Command.Middleware
{
    @Override
    public <R, C extends Command<R>> R invoke(C c, Next<R> next)
    {

        R response = next.invoke();
        return response;
    }
}
