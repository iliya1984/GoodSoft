package com.goodsoft.customersservice.logic.requests.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.customersservice.logic.annotations.CustomerRequestHanlder;
import org.springframework.stereotype.Service;

@CustomerRequestHanlder()
public class CreateCustomerRequestHandler implements Command.Handler<CreateCustomerRequest, CreateCustomerResultModel>
{
    @Override
    public CreateCustomerResultModel handle(CreateCustomerRequest createCustomerRequest) {
        return null;
    }
}
