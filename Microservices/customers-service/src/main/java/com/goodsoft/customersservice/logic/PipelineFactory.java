package com.goodsoft.customersservice.logic;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.goodsoft.customersservice.CustomersServiceApplication;
import com.goodsoft.customersservice.logic.annotations.CustomerRequestHanlder;
import com.goodsoft.customersservice.logic.middlewares.ValidationMiddleware;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequestHandler;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.GenericWebApplicationContext;


import java.util.Arrays;
import java.util.Locale;
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

        var beanNames = context.getBeanNamesForAnnotation(CustomerRequestHanlder.class);
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
        var requestClass = request.getClass();
        var requestResolvableType = ResolvableType.forClass(requestClass);
        var validationMiddlewareResolvableType = ResolvableType.forClassWithGenerics(ValidationMiddleware.class, requestResolvableType);
        var validationMiddlewareClass = validationMiddlewareResolvableType.toClass();
        var validatorMiddlewareTypeNames = context.getBeanNamesForType(validationMiddlewareResolvableType);

        if(validatorMiddlewareTypeNames.length == 0)
        {
            context.registerBean(validationMiddlewareClass, new ValidationMiddleware<C>());
        }

        validatorMiddlewareTypeNames = context.getBeanNamesForType(validationMiddlewareClass);
        if(validatorMiddlewareTypeNames.length > 0)
        {
            var validatorMiddlewareName = validatorMiddlewareTypeNames[0];
            return  (ValidationMiddleware<C>)context.getBean(validatorMiddlewareName);
        }

        return null;
    }
}
