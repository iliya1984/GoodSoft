package com.goodsoft.loggingconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.goodsoft" } )
public class LoggingConsumerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(LoggingConsumerApplication.class, args);
    }
}
