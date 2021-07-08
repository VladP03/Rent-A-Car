package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;

public class CarNotFoundException extends RuntimeException{
    private final Integer id;
    private String message = "Car not found.";

    public CarNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        message += " In database does not exists an car with id " + id + ".";

        return message;
    }
}
