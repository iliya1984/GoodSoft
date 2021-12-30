package com.goodsoft.customersservice.logic.search;

import an.awesome.pipelinr.Command;
import com.goodsoft.infra.mediator.annotations.RequestHanlder;

@RequestHanlder()
public class SearchCustomersQueryHandler implements Command.Handler<SearchCustomersQuery, Object>
{

    @Override
    public Object handle(SearchCustomersQuery searchCustomersQuery)
    {
        return null;
    }
}
