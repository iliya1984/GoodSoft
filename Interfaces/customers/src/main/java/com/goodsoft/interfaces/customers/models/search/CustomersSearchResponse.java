package com.goodsoft.interfaces.customers.models.search;

import lombok.Getter;
import lombok.Setter;

public class CustomersSearchResponse
{
    @Getter
    @Setter
    private SearchResultModel<CustomerSearchResultItemModel> result;
}
