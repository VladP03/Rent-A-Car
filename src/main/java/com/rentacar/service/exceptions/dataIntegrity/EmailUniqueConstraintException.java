package com.rentacar.service.exceptions.dataIntegrity;

import lombok.Getter;

@Getter
public class EmailConstraintException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public EmailConstraintException(Class className, String name) {
        this.message = "Email unique constraint violated on " + className.getSimpleName() + ", email: " + name + " already exists.";
        debugMessage = "Change email";
    }
}
