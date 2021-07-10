package com.rentacar.service.exceptions.city;

public class CityNotFoundException extends RuntimeException{
    private final Integer id;
    private String message = "City not found.";
    private String debugMessage = "No city in database with that ID";

    public CityNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        message += " In database does not exists an city with id " + id + ".";

        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
