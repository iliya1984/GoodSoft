package com.goodsoft.interfaces.customers.entities;

import lombok.Getter;
import lombok.Setter;

public class CustomerEmail
{
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private boolean isPrimary;
}
