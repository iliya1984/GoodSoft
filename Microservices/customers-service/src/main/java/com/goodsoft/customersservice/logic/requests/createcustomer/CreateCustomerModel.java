package com.goodsoft.customersservice.logic.requests.createcustomer;

import com.goodsoft.customersservice.entities.CustomerEmail;
import com.goodsoft.customersservice.entities.CustomerPhone;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CreateCustomerModel
{
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
}
