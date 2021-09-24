package com.goodsoft.infra.mediator.factory;


import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;
import com.goodsoft.infra.mediator.middlewares.ValidationMiddleware;
import com.goodsoft.infra.mediator.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;
import org.springframework.web.context.support.GenericWebApplicationContext;


import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class PipelineFactory implements IPipelineFactory
{
    public PipelineFactory()
    {

    }

    @Autowired
    private GenericWebApplicationContext context;

    @Bean
    @Override
    public <R, C extends  Command<R>> Pipeline getPipeline(C request) throws IllegalArgumentException {

        var beanNames = context.getBeanNamesForAnnotation(RequestHanlder.class);
        var requestClass = request.getClass();
        var requestName = requestClass.getSimpleName();
        var handlerName = Utils.firstCharToLowerCase(requestName + "Handler");

        if(Arrays.stream(beanNames).anyMatch(bn -> bn.equals(handlerName)))
        {
            var pipeline = new Pipelinr();

            var validationMiddleware = createValidationMiddleware(request);
            if(validationMiddleware != null)
            {
                pipeline.with(() -> Stream.of(validationMiddleware));
            }

            var requestHandler = (Command.Handler<C, R>)context.getBean(handlerName);
            pipeline.with(() -> Stream.of(requestHandler) );

            return pipeline;
        }
        else
        {
            String errorMessage = "Error occurred. Request handler for " + requestName + " was not found";
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private <R, C extends  Command<R>> ValidationMiddleware<C> createValidationMiddleware(C request)
    {
        Object bean = null;

        var requestClass = request.getClass();
        var requestResolvableType = ResolvableType.forClass(requestClass);
        var validationMiddlewareResolvableType = ResolvableType.forClassWithGenerics(ValidationMiddleware.class, requestResolvableType);

        var validationMiddlewareClass = validationMiddlewareResolvableType.toClass();

        try
        {
            bean =  context.getBean(validationMiddlewareClass);
        }
        catch(Exception ex)
        {
            bean = null;
        }

        if(bean != null)
        {
            return (ValidationMiddleware<C>)bean;
        }

        var validatorMiddlewareTypeNames = context.getBeanNamesForType(validationMiddlewareResolvableType);

        if(validatorMiddlewareTypeNames.length == 0)
        {
            context.registerBean(validationMiddlewareClass);
        }

        validatorMiddlewareTypeNames = context.getBeanNamesForType(validationMiddlewareClass);
        if(validatorMiddlewareTypeNames.length > 0)
        {
            var validatorMiddlewareName = validatorMiddlewareTypeNames[0];
            bean =  context.getBean(validationMiddlewareClass);
            return (ValidationMiddleware<C>)bean;
        }

        return null;
    }
}