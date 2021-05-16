package com.goodsoft.customersservice.logic;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.goodsoft.customersservice.CustomersServiceApplication;
import com.goodsoft.customersservice.logic.annotations.CustomerRequestHanlder;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequestHandler;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


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
    public <R, C extends  Command<R>> Pipeline getPipeline(String requestName) {

        var handlerName = requestName + "Handler";
        var requestHandler = (Command.Handler<C, R>)context.getBean(handlerName);

        var pipeline = new Pipelinr()
                .with(() -> Stream.of(requestHandler) );
        return pipeline;
    }
}
