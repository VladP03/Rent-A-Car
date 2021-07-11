package com.rentacar.service.exceptions.car;


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


    @Override
    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
