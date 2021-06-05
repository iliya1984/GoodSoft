package com.goodsoft.infra.microservice;

import com.goodsoft.infra.mediator.errorhandling.ValidationException;
import com.goodsoft.infra.microservice.models.UnprocessableEntityModel;
import com.goodsoft.infra.microservice.validation.ValidationError;
import com.goodsoft.infra.microservice.validation.ValidationFailure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class RestService
{
    protected ResponseEntity<?> exceptionResponse(Exception ex)
    {
        var exceptionName = ex.getClass().getSimpleName();
        if(exceptionName.equals("ValidationException"))
        {
            var validationException = (ValidationException)ex;

            var invalidResponse = new UnprocessableEntityModel();
            var failures = new ArrayList<ValidationFailure>();

            validationException.getErrors().forEach(e ->
            {
                var error = new ValidationError();
                error.setErrorMessage(e.getMessage());
                error.setErrorCode(e.getCode());

                var fieldName = e.getField();
                var failure = failures.stream()
                        .filter(f -> f.getFieldName().equals(fieldName))
                        .findAny()
                        .orElse(null);

                if(failure != null)
                {
                    failure.getErrors().add(error);
                }
                else
                {
                    failure = new ValidationFailure();
                    failure.setFieldName(fieldName);
                    failure.getErrors().add(error);
                    failures.add(failure);
                }
            });

            invalidResponse.setFailureList(failures);
            return new ResponseEntity(invalidResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
