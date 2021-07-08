package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;

public class CarFuelException extends RuntimeException{
    private final CarDTO carDTO;
    private String message = "Car fuel is incorrect.";
    private String debugMessage = "Car fuel might be only: gas, diesel, hybrid and electric";

    public CarFuelException(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    @Override
    public String getMessage() {
        message += " Fuel introduced: " + carDTO.getFuel() + ".";
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
