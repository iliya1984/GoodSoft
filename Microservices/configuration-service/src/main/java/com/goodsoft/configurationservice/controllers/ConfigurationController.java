package com.goodsoft.configurationservice.controllers;

import com.goodsoft.configurationservice.dtos.DomainConfigurationRequest;
import com.goodsoft.configurationservice.logic.IConfigurationServiceManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController
{
    private IConfigurationServiceManager _manager;

    public ConfigurationController(IConfigurationServiceManager manager)
    {
        _manager = manager;
    }

    @GetMapping
    @RequestMapping("/domains/{domainName}/{key}")
    public ResponseEntity<Object> getDomainConfiguration(@PathVariable("domainName")String domainName, @PathVariable("key")String key)
    {
        try
        {
            var request = new DomainConfigurationRequest();
            request.setDomainName(domainName);
            request.setKey(key);

            var configuration = _manager.getDomainConfiguration(request);
            return ResponseEntity.ok(configuration);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
