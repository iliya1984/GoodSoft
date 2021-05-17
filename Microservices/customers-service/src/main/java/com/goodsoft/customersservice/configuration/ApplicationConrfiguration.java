package com.goodsoft.customersservice.configuration;

import br.com.fluentvalidator.AbstractValidator;
import com.goodsoft.customersservice.logic.IPipelineFactory;
import com.goodsoft.customersservice.logic.PipelineFactory;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerRequest;
import com.goodsoft.customersservice.logic.requests.createcustomer.CreateCustomerRequestValidator;
import org.apache.naming.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ApplicationConrfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
