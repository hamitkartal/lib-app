package com.library.library_app.dto;

import org.springframework.http.HttpStatus;

public class ServiceResponse {

    private final HttpStatus status;
    private final String message;
    private final Object responseObject;


    public ServiceResponse(HttpStatus status, String message, Object responseObject) {
        this.status = status;
        this.message = message;
        this.responseObject = responseObject;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getResponseObject() {
        return responseObject;
    }
}
