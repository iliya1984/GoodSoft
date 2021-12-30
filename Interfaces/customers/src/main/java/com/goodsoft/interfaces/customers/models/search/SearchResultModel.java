package com.goodsoft.interfaces.customers.models.search;

import com.goodsoft.interfaces.customers.models.errorhandling.CustomersServiceError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchResultModel<T>
{
    @Getter
    @Setter
    private List<CustomersServiceError> errors;

    @Getter
    @Setter
    private List<T> items;

    public boolean isSuccess()
    {
        return errors == null || errors.stream().count() == 0;
    }
}
