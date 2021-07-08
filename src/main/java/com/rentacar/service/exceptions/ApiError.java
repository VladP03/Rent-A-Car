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
    private String debugMessage;

    public ApiError(HttpStatus httpStatus, String message, String debugMessage) {
        this.timestamp = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = debugMessage;
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

    public String getDebugMessage() {
        return debugMessage;
    }

    public ApiError setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
        return this;
    }
}
