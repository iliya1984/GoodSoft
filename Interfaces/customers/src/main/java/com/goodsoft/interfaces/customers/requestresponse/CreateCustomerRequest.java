package com.goodsoft.interfaces.customers.requestresponse;

import lombok.Getter;
import lombok.Setter;

public class CreateCustomerRequest
{
    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String phone;
}

