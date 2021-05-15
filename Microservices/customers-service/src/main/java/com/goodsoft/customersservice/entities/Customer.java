package com.goodsoft.customersservice.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Customer
{
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String friendlyId;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private List<CustomerEmail> emails;

    @Getter
    @Setter
    private List<CustomerPhone> phones;
}
