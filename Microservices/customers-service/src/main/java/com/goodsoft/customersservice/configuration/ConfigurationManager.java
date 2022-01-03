package com.goodsoft.customersservice.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodsoft.infra.modulecore.configuration.ConfigurationSection;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ConfigurationManager implements ICustomersConfigurationManager
{
    private CustomerServiceConfiguration _section;
    private ILogger _logger;

    @Autowired
    private Environment _environment;

    public ConfigurationManager(ILogger logger)
    {
        _logger = logger;
    }

    @Override
    public CustomerServiceConfiguration getConfiguration()
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
                    return response.bodyToMono(ConfigurationSection.class);
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
                    _logger.logError(ex);
                }
            }
        }

        return _section;
    }
}
