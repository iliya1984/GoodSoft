package com.goodsoft.customersservice.logic;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;

public interface IPipelineFactory
{
    <R, C extends  Command<R>> Pipeline getPipeline(String requestName) throws IllegalArgumentException;
}
