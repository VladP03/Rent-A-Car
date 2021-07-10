package com.rentacar.service.exceptions.city;


import com.rentacar.model.CityDTO;

public class CityAlreadyExistsException extends RuntimeException{
    private final CityDTO cityDTO;
    private String message = "City already exists.";
    private final String debugMessage = "Try a different name.";

    public CityAlreadyExistsException(CityDTO cityDTO) {
        this.cityDTO = cityDTO;
    }

    @Override
    public String getMessage() {
        message += " Error on the following city: " + cityDTO.getName();

        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
