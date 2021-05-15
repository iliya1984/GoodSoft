package com.goodsoft.customersservice.logic;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.goodsoft.customersservice.logic.requests.getcustomer.GetCustomerRequestHandler;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

public class PipelineFactory implements IPipelineFactory
{
    @Bean
    @Override
    public <R, C extends  Command<R>> Pipeline getPipeline(Command.Handler<C, R> requestHanlder) {

        var pipeline = new Pipelinr()
                .with(() -> Stream.of(requestHanlder) );
        return pipeline;
    }
}
