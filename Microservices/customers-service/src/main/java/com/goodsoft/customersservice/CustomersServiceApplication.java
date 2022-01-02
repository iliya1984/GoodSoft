package com.goodsoft.customersservice;
import com.goodsoft.customersservice.configuration.ConfigurationManager;
import com.goodsoft.customersservice.configuration.CustomerServiceConfiguration;
import com.goodsoft.infra.mediator.factory.IPipelineFactory;
import com.goodsoft.infra.mediator.factory.PipelineFactory;
import com.goodsoft.infra.modulecore.configuration.IConfigurationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = { "com.goodsoft" } )
public class CustomersServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(CustomersServiceApplication.class, args);
	}
}
