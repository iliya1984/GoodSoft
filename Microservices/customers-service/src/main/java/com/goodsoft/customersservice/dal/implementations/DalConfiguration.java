package com.goodsoft.customersservice.dal.implementations;

import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.baeldung.spring.data.es.repository")
@ComponentScan(basePackages = { "com.baeldung.spring.data.es.service" })
public class DalConfiguration
{
    @Bean
    public RestHighLevelClient client(CustomerServiceConfiguration configuration)
    {
        var searchServer = configuration.getSearchServer();

        if(searchServer == null)
        {
            throw new NullPointerException("Unable to create RestHighLevelClient, search server configuratio is NULL");
        }

        var host = searchServer.getHost();
        var port = searchServer.getPort();

        var connectionString =  host +":" + String.valueOf(port);
        var clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(connectionString)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(CustomerServiceConfiguration configuration)
    {
        var client = client(configuration);
        return new ElasticsearchRestTemplate(client);
    }

}
