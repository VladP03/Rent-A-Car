package com.rentacar.service.exceptions.car;


public class CarNotFoundException extends RuntimeException{
    private Integer id = null;
    private String brandName = null;
    private String message = "Car not found.";
    private String debugMessage = "No car in database with that ID";

    public CarNotFoundException(Integer id) {
        this.id = id;
    }

    public CarNotFoundException(String brandName) {
        this.brandName = brandName;
    }


    @Override
    public String getMessage() {
        if (brandName == null) {
            message += " In database does not exists an car with id " + id + ".";
        } else {
            message += " In database does not exists an car with brand name " + brandName + ".";
        }

        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
