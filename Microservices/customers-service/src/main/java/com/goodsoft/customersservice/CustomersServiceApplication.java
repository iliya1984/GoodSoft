package com.goodsoft.customersservice;
import com.goodsoft.customersservice.configuration.ConfigurationManager;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.customersservice.configuration.IConfigurationManager;
import com.goodsoft.customersservice.dal.abstractions.ICustomersDal;
import com.goodsoft.customersservice.dal.implementations.CustomersDal;
import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import com.goodsoft.infra.mediator.factory.PipelineFactory;
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
	public IConfigurationManager configurationManager()
	{
		return new ConfigurationManager();
	}

	@Bean
	public CustomerServiceConfiguration configuration(IConfigurationManager configurationManager)
	{
		return configurationManager.GetConfiguration();
	}
}
