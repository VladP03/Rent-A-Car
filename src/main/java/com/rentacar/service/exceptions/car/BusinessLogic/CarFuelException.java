package com.rentacar.service.exceptions.car.BusinessLogic;

import com.rentacar.model.CarDTO;
import lombok.Getter;

@Getter
public class CarFuelException extends CarBusinessLogicException {
    private String message = "Car fuel is incorrect.";
    private String debugMessage;


    public CarFuelException() {}

    public CarFuelException(CarDTO carDTO) {
        message += " Fuel introduced: " + carDTO.getFuel() + ".";
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        debugMessage = "Car fuel might be only: gas, diesel, hybrid and electric";
    }
}
