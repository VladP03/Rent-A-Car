package com.rentacar.service.exceptions.city;


import com.rentacar.model.CityDTO;

public class CityAlreadyExistsException extends RuntimeException{
    private String message = "City already exists.";
    private final String debugMessage;

    public CityAlreadyExistsException(CityDTO cityDTO) {
        message += " Error on the following city: " + cityDTO.getName();
        debugMessage = "Try a different name.";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
