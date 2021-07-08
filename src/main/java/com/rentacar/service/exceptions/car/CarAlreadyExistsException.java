package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;

public class CarAlreadyExistsException extends RuntimeException{
    private final CarDTO carDTO;
    private String message = "Car already exists.";

    public CarAlreadyExistsException(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    @Override
    public String getMessage() {
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        return message;
    }
}
