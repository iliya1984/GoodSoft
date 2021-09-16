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
    private ConfigurationSection _section;

    @Autowired
    private Environment _environment;

    @Override
    public ConfigurationSection GetConfiguration()
    {
        if(_section == null)
        {
            var baseUrl = _environment.getProperty("configuration-service-baseurl");
            var microserviceName = _environment.getProperty("microservice.name");
            var uri = "/microservices/" + microserviceName;

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
                    var section = objectMapper.readValue(value, ConfigurationSection.class);
                    _section = section;
                }
                catch(JsonProcessingException ex)
                {
                   //TODO: Log error
                }
            }
        }

        return _section;
    }
}
