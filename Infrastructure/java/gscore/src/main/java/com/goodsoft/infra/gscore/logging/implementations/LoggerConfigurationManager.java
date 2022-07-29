package com.goodsoft.infra.gscore.logging.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodsoft.infra.gscore.configuration.ConfigurationSection;
import com.goodsoft.infra.gscore.configuration.IConfigurationManager;
import com.goodsoft.infra.gscore.logging.models.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LoggerConfigurationManager implements IConfigurationManager<LoggingConfiguration>
{
    private static LoggingConfiguration _section;


    @Autowired
    private Environment _environment;

    public LoggingConfiguration getConfiguration()
    {
        if(_section == null)
        {
            var baseUrl = _environment.getProperty("configuration-service-baseurl");
            var loggingDomainName = _environment.getProperty("logging.domain.name");
            var loggingConfigurationKey = _environment.getProperty("logging.configuration.key");
            var uri = "/domains/" + loggingDomainName + "/" + loggingConfigurationKey;

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

                    var domainName = _environment.getProperty("domain.name");
                    if(domainName != null && false == domainName.isEmpty())
                    {
                        section.setLoggingDomainName(domainName);
                    }
                    var moduleName = _environment.getProperty("module.name");
                    if(moduleName != null && false == moduleName.isEmpty())
                    {
                        section.setLoggingModuleName(moduleName);
                    }

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
