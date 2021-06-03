package com.goodsoft.infra.mediator.factory;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;

public interface IPipelineFactory
{
    <R, C extends  Command<R>> Pipeline getPipeline(C request) throws IllegalArgumentException;
}
