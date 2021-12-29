package com.goodsoft.customersservice.entities.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchQuery
{
    @Getter
    @Setter
    private List<SearchFilter> filters;

    @Getter
    @Setter
    private SortOptions sortOptions;

    @Getter
    @Setter
    private String searchTerm;

    @Getter
    @Setter
    private Integer skip;

    @Getter
    @Setter
    private Integer take;
}
