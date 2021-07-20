package com.rentacar.service.exceptions.car;

import com.rentacar.model.RentDTO;
import lombok.Getter;

@Getter
public class CarNotFoundException extends RuntimeException{
    private String message = "Car not found.";
    private final String debugMessage;

    public CarNotFoundException(Integer id) {
        message += " In database does not exists an car with id " + id + ".";
        debugMessage = "No car in database with that ID";
    }

    public CarNotFoundException(String brandName) {
        message += " In database does not exists an car with brand name " + brandName + ".";
        debugMessage = "No car in database with that brand name";
    }

    public CarNotFoundException(Integer id, String brandName) {
        message += " In database does not exists an car with id " + id + " and brand name " + brandName + ".";
        debugMessage = "No car in database with that ID and brand name";
    }

    public CarNotFoundException(RentDTO rentDTO) {
        message += " This dealership does not own an car with name " + rentDTO.getCar().getBrandName() + " " + rentDTO.getCar().getName() + " with VIN: " + rentDTO.getCar().getVIN();
        debugMessage = "Change car";
    }
}
