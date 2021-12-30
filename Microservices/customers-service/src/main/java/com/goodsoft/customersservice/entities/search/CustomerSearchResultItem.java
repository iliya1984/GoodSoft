package com.goodsoft.customersservice.entities.search;

import com.goodsoft.customersservice.entities.customers.CustomerEmailEntity;
import com.goodsoft.customersservice.entities.customers.CustomerPhoneEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

public class CustomerSearchResultItem
{
    @Getter
    @Setter
    private String customerId;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String primaryEmail;

    @Getter
    @Setter
    private String primaryPhone;
}
