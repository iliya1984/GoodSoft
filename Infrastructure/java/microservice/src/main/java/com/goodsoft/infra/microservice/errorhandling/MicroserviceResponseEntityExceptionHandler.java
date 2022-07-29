package com.goodsoft.infra.microservice.errorhandling;

import com.goodsoft.infra.modulecore.logging.abstractions.ILogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MicroserviceResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private ILogger _logger;

    public MicroserviceResponseEntityExceptionHandler(ILogger logger)
    {
        _logger = logger;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest webRequest) {

        var correlationId = webRequest.getHeader("GS-CorrelationId");

        logError(exception);

        var response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        response.setCorrelationId(correlationId);

        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    private void logError(Exception exception)
    {
        _logger.logError(exception);
    }
}