package com.goodsoft.customersservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.goodsoft" } )
public class CustomersServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(CustomersServiceApplication.class, args);
	}
}
