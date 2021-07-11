package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;

public class CarAlreadyExistsException extends RuntimeException{
    private String message = "Car already exists.";
    private final String debugMessage;

    public CarAlreadyExistsException(CarDTO carDTO) {
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";
        debugMessage = "Be careful at VIN, a car already exists with that VIN";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
