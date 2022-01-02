package com.goodsoft.infra.microservice.errorhandling;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ExceptionResponse
{
    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private LocalDateTime dateTime;

    @Getter
    @Setter
    private String correlationId;

}
