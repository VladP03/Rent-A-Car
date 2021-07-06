package com.rentacar.service.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ApiError implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy - hh:mm:ss")
    private final LocalDateTime timestamp;
    private HttpStatus httpStatus;
    private String message;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ApiError setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiError setMessage(String message) {
        this.message = message;
        return this;
    }
}
