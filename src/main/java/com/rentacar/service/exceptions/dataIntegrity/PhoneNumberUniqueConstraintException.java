package com.rentacar.service.exceptions.dataIntegrity;

import com.rentacar.model.CountryDTO;
import lombok.Getter;

@Getter
public class PhoneNumberUniqueConstraintException extends RuntimeException{
    private final String message;
    private final String debugMessage;

    public PhoneNumberUniqueConstraintException(CountryDTO countryDTO) {
        this.message = "Phone number unique constraint violated on Country, phone number: " + countryDTO.getPhoneNumber() + " already exists.";
        debugMessage = "Change email";
    }
}
