package com.goodsoft.consumersindexer.dal.abstractions;

import com.goodsoft.consumersindexer.models.CustomerEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ICustomersRepository extends ElasticsearchRepository<CustomerEntry, String>
{


}
