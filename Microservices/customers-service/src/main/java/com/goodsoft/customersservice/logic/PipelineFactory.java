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
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.stream.Stream;

public class PipelineFactory implements IPipelineFactory
{
    public PipelineFactory()
    {

    }

    @Autowired
    private ApplicationContext context;

    @Bean
    @Override
    public <R, C extends  Command<R>> Pipeline getPipeline(String requestName) throws IllegalArgumentException {

        var beanNames = context.getBeanNamesForAnnotation(CustomerRequestHanlder.class);
        var handlerName = requestName + "Handler";

        if(Arrays.stream(beanNames).anyMatch(bn -> bn.equals(handlerName)))
        {
            var validator = new ValidationMiddleware<C>();
            var requestHandler = (Command.Handler<C, R>)context.getBean(handlerName);

            var pipeline = new Pipelinr()
                    .with(() -> Stream.of(validator))
                    .with(() -> Stream.of(requestHandler) );
            return pipeline;
        }
        else
        {
            String errorMessage = "Error occurred. Request handler for " + requestName + " was not found";
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
