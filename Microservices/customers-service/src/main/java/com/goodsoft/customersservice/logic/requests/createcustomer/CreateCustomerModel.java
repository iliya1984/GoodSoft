package com.goodsoft.customersservice.logic.requests.createcustomer;

import com.goodsoft.customersservice.entities.CustomerEmail;
import com.goodsoft.customersservice.entities.CustomerPhone;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CreateCustomerModel
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
