package com.product.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime timestamp;

    private Integer status;
    private HttpStatus error;
    private String messsage;
    private String path;

    public ExceptionResponse(){
        super();

    }

    // Getters 
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public Integer getStatus() {
        return status;
    }

    public HttpStatus getError() {
        return error;
    }
    public String getMesssage() {
        return messsage;
    }
    public String getPath() {
        return path;
    }

    // Setters 
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public void setError(HttpStatus error) {
        this.error = error;
    }
    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
