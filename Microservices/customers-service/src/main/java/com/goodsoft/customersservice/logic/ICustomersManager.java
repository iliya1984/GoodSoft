package com.goodsoft.customersservice.logic;

import com.goodsoft.customersservice.requests.GetCustomerByIdRequest;
import com.goodsoft.customersservice.responses.GetCustomerByIdResponse;

public interface ICustomersManager
{
    GetCustomerByIdResponse GetById(GetCustomerByIdRequest request);
}
