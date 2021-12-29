package com.goodsoft.customersservice.entities.customers;

import lombok.Getter;
import lombok.Setter;

public class CustomerPhoneEntity
{
    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private boolean isPrimary;
}
