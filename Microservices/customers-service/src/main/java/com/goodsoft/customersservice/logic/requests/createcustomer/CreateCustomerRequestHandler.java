package com.goodsoft.customersservice.logic.requests.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;

@RequestHanlder()
public class CreateCustomerRequestHandler implements Command.Handler<CreateCustomerRequest, CreateCustomerResultModel>
{
    @Override
    public CreateCustomerResultModel handle(CreateCustomerRequest createCustomerRequest) {
        return null;
    }
}
