package com.rentacar.service.exceptions.dataIntegrity;

import lombok.Getter;

@Getter
public class CityNameUniqueConstraintException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public CityNameUniqueConstraintException(Class className, String name) {
        this.message = "City name unique constraint violated on " + className.getSimpleName() + ", name: " + name + " already exists.";
        debugMessage = "Change email";
    }
}
