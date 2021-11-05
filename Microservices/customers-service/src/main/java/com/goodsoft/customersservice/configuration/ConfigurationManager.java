package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ConfigurationManager implements IConfigurationManager
{
    private CustomerServiceConfiguration _section;

    @Autowired
    private Environment _environment;

    @Override
    public CustomerServiceConfiguration GetConfiguration()
    {
        if(_section == null)
        {
            var baseUrl = _environment.getProperty("configuration-service-baseurl");
            var domainName = _environment.getProperty("domain.name");
            var key = _environment.getProperty("configuration.key");
            var uri = "/domains/" + domainName + "/" + key;

            var client = WebClient.create(baseUrl);

            var headerSpec = client.get();
            headerSpec.uri(uri);

            var monoResponse = headerSpec.exchangeToMono(response ->
            {
                if (response.statusCode().equals(HttpStatus.OK))
                {
                    return response.bodyToMono(ConfigurationDto.class);
                }
                else
                {
                    return response.createException().flatMap(Mono::error);
                }
            });

            var configurationDto =  monoResponse.block();

            if(configurationDto != null && configurationDto.getValue() != null)
            {
                try
                {
                    var value = configurationDto.getValue();
                    var objectMapper = new ObjectMapper();
                    var section = objectMapper.readValue(value, CustomerServiceConfiguration.class);
                    _section = section;
                }
                catch(JsonProcessingException ex)
                {
                    int i = 1;
                   //TODO: Log error
                }
            }
        }

        return _section;
    }
}