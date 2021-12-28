package com.goodsoft.customersservice.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerEntity
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
    private List<CustomerEmailEntity> emails;

    @Getter
    @Setter
    private List<CustomerPhoneEntity> phones;

    public CustomerEntity()
    {
        emails = new ArrayList<>();
        phones = new ArrayList<>();
    }
}
