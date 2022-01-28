package com.goodsoft.configurationservice;

import com.goodsoft.configurationservice.logic.ConfigurationServiceManager;
import com.goodsoft.configurationservice.logic.IConfigurationServiceManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConfigurationServiceApplication extends SpringBootServletInitializer {

	//Mongo: connection string = mongodb://localhost:27017
	public static void main(String[] args)
	{
		SpringApplication.run(ConfigurationServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ConfigurationServiceApplication.class);
	}

	@Bean
	public IConfigurationServiceManager configurationServiceManager()
	{
		return new ConfigurationServiceManager();
	}
}
