package com.rentacar.service.exceptions.country;

import com.rentacar.model.CountryDTO;

public class CountryAlreadyExistsException extends RuntimeException {
    private final String message;
    private String debugMessage;

    public CountryAlreadyExistsException(CountryDTO countryDTO, String... parameters) {
        message = "Country already exists. Error on the following country: " + countryDTO.getName();

        if (parameters != null) {
            if (parameters.length == 1) {
                debugMessage = "Be careful at these parameter: " + parameters[0];
            } else {
                debugMessage = "Be careful at these parameters: ";
                for (int i=0;i<parameters.length-1;i++) {
                    debugMessage += parameters[i] + ", ";
                }
                debugMessage += parameters[parameters.length-1] + ".";
            }
        } else {
            debugMessage = "";
        }
    }


    @Override
    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
