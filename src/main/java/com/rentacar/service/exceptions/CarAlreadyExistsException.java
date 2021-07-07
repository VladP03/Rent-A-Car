package com.rentacar.service.exceptions;

public class CarAlreadyExistsException extends RuntimeException{
    public CarAlreadyExistsException(String message) {
        super(message);
    }
}
