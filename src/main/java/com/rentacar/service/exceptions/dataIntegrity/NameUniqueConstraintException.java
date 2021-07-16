package com.rentacar.service.exceptions.dataIntegrity;

import lombok.Getter;

@Getter
public class NameUniqueConstraintException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public NameUniqueConstraintException(Class className) {
        this.message = "Name unique constraint violated on " + className.getSimpleName();
        debugMessage = "Change name";
    }

    public NameUniqueConstraintException(Class className, String name) {
        this.message = "Name unique constraint violated on " + className.getSimpleName() + ", name: " + name + " already exists.";
        debugMessage = "Change name";
    }
}
