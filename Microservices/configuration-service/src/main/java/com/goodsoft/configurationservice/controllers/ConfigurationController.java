package com.goodsoft.configurationservice.controllers;

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
    @RequestMapping("/microservices/{serviceName}")
    public ResponseEntity<Object> getMicroserviceConfiguration(@PathVariable("serviceName")String serviceName)
    {
        try
        {
            var configuration = _manager.getMicroserviceConfiguration(serviceName);
            return ResponseEntity.ok(configuration);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
