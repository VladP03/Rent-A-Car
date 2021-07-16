package com.rentacar.service.exceptions.dataIntegrity;

import lombok.Getter;

@Getter
public class PhoneNumberUniqueConstraintException extends RuntimeException{
    private final String message;
    private final String debugMessage;

    public PhoneNumberUniqueConstraintException(Class className, String phoneNumber) {
        this.message = "Phone number unique constraint violated on " + className.getSimpleName() + ", phone number: " + phoneNumber + " already exists.";
        debugMessage = "Change email";
    }
}
