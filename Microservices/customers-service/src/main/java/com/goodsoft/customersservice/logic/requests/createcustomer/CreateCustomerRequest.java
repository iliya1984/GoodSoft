package com.goodsoft.customersservice.logic.requests.createcustomer;

import an.awesome.pipelinr.Command;
import lombok.Getter;
import lombok.Setter;

public class CreateCustomerRequest implements Command<CreateCustomerResultModel>
{
    @Getter
    @Setter
    private CreateCustomerModel model;
}
