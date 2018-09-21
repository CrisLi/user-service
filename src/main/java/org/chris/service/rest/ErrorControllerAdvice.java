package org.chris.service.rest;

import java.util.stream.Collectors;

import org.chris.service.exception.BadRequestException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public void handleException(WebExchangeBindException exception) {

        String message = exception.getFieldErrors()
            .stream()
            .map(error -> error.getField() + " " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

        throw new BadRequestException(message);
    }

}
