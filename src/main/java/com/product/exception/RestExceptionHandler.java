package com.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionResponse> handleApiException(ApiException exception, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(exception.getStatus().value());
        response.setError(exception.getStatus());
        response.setMesssage(exception.getMessage());
        response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        return new  ResponseEntity<>(response, response.getError());

    }

    @ExceptionHandler(DBAccessException.class)
    protected ResponseEntity<ExceptionResponse> DBAccessException(DBAccessException exception, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMesssage("Error al acceder a la base de datos");
        response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        return new  ResponseEntity<>(response, response.getError());

    }

    
}
