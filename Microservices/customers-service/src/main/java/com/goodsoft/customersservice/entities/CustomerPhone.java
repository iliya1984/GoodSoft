package com.goodsoft.customersservice.entities;

import lombok.Getter;
import lombok.Setter;

public class CustomerPhone
{
    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private boolean isPrimary;

    @Getter
    @Setter
    private CustomerPhoneCategory category = CustomerPhoneCategory.Personal;

    @Getter
    @Setter
    private boolean isMobile = true;
}
