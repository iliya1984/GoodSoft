package com.goodsoft.customersservice.logic.updatecustomer;

import an.awesome.pipelinr.Command;
import com.goodsoft.interfaces.customers.models.customers.UpdateCustomerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class UpdateCustomerCommand implements Command<UUID>
{
    @Getter
    @Setter
    private UpdateCustomerModel model;
}
