package com.goodsoft.interfaces.customers.models.customers;

import lombok.Getter;
import lombok.Setter;

public class CustomerEmailModel
{
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private boolean isPrimary;
}
