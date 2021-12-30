package com.goodsoft.customersservice.logic.search;

import an.awesome.pipelinr.Command;
import lombok.Getter;
import lombok.Setter;

public class SearchCustomersQuery implements Command<Object>
{
    @Getter
    @Setter
    private String searchTerm;

    @Getter
    @Setter
    private String filters;

    @Getter
    @Setter
    private int skip;

    @Getter
    @Setter
    private int take;
}
