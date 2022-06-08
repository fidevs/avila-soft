package com.fidev.avilasoft.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResponseException.class)
    public HttpEntity<Object> handleResponseException(ResponseException e) {
        return new ResponseEntity<>(e.toResponse(), HttpStatus.CONFLICT);
    }
}
