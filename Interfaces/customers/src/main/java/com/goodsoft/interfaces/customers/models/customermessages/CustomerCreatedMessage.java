package com.goodsoft.interfaces.customers.models.customermessages;

import lombok.Getter;
import lombok.Setter;

public class CustomerCreatedMessage
{
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
