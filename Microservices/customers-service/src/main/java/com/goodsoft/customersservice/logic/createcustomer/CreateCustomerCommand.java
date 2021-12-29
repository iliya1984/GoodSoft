package com.goodsoft.customersservice.logic.createcustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.interfaces.customers.models.customers.CreateCustomerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class CreateCustomerCommand implements Command<UUID>
{
    @Getter
    @Setter
    private CreateCustomerModel model;
}
