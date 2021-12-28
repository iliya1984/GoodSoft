package com.goodsoft.interfaces.customers.models.customers;

import lombok.Getter;
import lombok.Setter;

public class CustomerPhoneModel
{
    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private boolean isPrimary;
}
