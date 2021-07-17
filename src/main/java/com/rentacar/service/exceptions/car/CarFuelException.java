package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;
import lombok.Getter;

@Getter
public class CarFuelException extends RuntimeException{
    private String message = "Car fuel is incorrect.";
    private final String debugMessage;

    public CarFuelException(CarDTO carDTO) {
        message += " Fuel introduced: " + carDTO.getFuel() + ".";
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        debugMessage = "Car fuel might be only: gas, diesel, hybrid and electric";
    }
}
