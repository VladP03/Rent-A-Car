package com.rentacar.service.exceptions.country;

public class CountryNotFoundException extends RuntimeException {
    private String message = "Country not found.";
    private final String debugMessage;

    public CountryNotFoundException(Integer id) {
        message += " In database does not exists an country with id " + id + ".";
        debugMessage = "No country in database with that ID";
    }

    public CountryNotFoundException(String name) {
        message += " In database does not exists an country with name " + name + ".";
        debugMessage = "No country in database with that name";
    }

    public CountryNotFoundException(Integer id, String name) {
        message += " In database does not exists an country with id " + id + " and name " + name + ".";
        debugMessage = "No country in database with that ID and name";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
