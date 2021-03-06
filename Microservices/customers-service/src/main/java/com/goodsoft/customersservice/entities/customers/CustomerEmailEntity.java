package com.goodsoft.customersservice.entities.customers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CustomerEmailEntity
{
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private boolean isPrimary;
}
