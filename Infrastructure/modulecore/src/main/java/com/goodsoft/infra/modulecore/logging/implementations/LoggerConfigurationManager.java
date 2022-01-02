package com.goodsoft.infra.modulecore.logging.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodsoft.infra.modulecore.configuration.ConfigurationSection;
import com.goodsoft.infra.modulecore.logging.models.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LoggerConfigurationManager
{
    private LoggingConfiguration _section;

    @Autowired
    private Environment _environment;

    public LoggingConfiguration getConfiguration()
    {
        if(_section == null)
        {
            var baseUrl = _environment.getProperty("configuration-service-baseurl");
            var domainName = _environment.getProperty("logging.domain.name");
            var key = _environment.getProperty("logging.configuration.key");
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
                    var section = objectMapper.readValue(value, LoggingConfiguration.class);
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
