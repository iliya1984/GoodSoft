package com.goodsoft.interfaces.customers.models.search;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class CustomerSearchResultItemModel
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
    private String email;

    @Getter
    @Setter
    private String phone;
}
