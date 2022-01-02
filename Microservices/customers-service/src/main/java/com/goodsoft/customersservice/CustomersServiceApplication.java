package com.goodsoft.customersservice;
import com.goodsoft.customersservice.configuration.ConfigurationManager;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import com.goodsoft.infra.mediator.factory.PipelineFactory;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomersServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(CustomersServiceApplication.class, args);
	}

	@Bean
	public IPipelineFactory pipelineFactory()
	{
		return new PipelineFactory();
	}

	@Bean
	public CustomerServiceConfiguration configuration(IConfigurationManager<CustomerServiceConfiguration> configurationManager)
	{
		return configurationManager.getConfiguration();
	}
}
