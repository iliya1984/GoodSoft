package com.goodsoft.customersservice;

import com.goodsoft.customersservice.logic.IPipelineFactory;
import com.goodsoft.customersservice.logic.PipelineFactory;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

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
}
