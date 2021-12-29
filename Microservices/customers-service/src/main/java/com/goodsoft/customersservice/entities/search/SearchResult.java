package com.goodsoft.customersservice.entities.search;

import com.goodsoft.customersservice.entities.errors.CustomersServiceError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchResult<T>
{
    public boolean isSuccess()
    {
        return errors == null || errors.stream().count() == 0;
    }

    @Getter
    @Setter
    private List<CustomersServiceError> errors;

    @Getter
    @Setter
    private List<T> items;
}
