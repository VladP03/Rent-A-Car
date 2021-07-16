package com.rentacar.service.exceptions.dataIntegrity;

import lombok.Getter;

@Getter
public class PhoneNumberUniqueConstrantException extends RuntimeException{
    private final String message;
    private final String debugMessage;

    public PhoneNumberUniqueConstrantException(Class className, String phoneNumber) {
        this.message = "Phone number unique constraint violated on " + className.getSimpleName() + ", phone number: " + phoneNumber + " already exists.";
        debugMessage = "Change email";
    }
}
