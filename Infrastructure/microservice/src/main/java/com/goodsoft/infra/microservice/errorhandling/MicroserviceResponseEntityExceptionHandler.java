package com.goodsoft.infra.microservice.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MicroserviceResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest webRequest) {

        var response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Internal Server Error");

        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }
}