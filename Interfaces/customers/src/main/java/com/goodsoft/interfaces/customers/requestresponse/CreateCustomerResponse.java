package com.goodsoft.interfaces.customers.requestresponse;

import lombok.Getter;
import lombok.Setter;

public class CreateCustomerResponse extends CustomersServiceResponse
{
    @Getter
    @Setter
    private Long customerId;
}
