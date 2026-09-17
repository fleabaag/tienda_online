package com.product.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    /* GETTERS */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public HttpStatus getStatus() {
        return status;
    }

    /* SETTERS */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }



}
