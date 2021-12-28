package com.goodsoft.interfaces.customers.models.customers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CustomerModel
{
    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private List<CustomerEmailModel> email;

    @Getter
    @Setter
    private List<CustomerPhoneModel> phone;
}
