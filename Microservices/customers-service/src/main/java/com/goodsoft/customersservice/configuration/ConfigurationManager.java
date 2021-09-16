package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ConfigurationManager implements IConfigurationManager
{
    private ConfigurationSection _section;


    @Override
    public ConfigurationSection GetConfiguration()  {
        if(_section == null)
        {
            var client = WebClient.create("http://configuration.goodsoft.com:8081");

            var headerSpec = client.get();
            headerSpec.uri("/microservices/customersservice");
            Mono<ConfigurationDto> monoResponse = headerSpec.exchangeToMono(response -> {

                if (response.statusCode().equals(HttpStatus.OK)) {
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
