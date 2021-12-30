package com.goodsoft.interfaces.customers.models.customers;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerModel
{
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private List<CustomerEmailModel> emails;

    @Getter
    @Setter
    private List<CustomerPhoneModel> phones;

    public CustomerModel()
    {
        emails = new ArrayList<>() ;
        phones = new ArrayList<>();
    }
}
