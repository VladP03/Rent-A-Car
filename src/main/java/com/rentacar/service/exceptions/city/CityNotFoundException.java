package com.rentacar.service.exceptions.city;

import lombok.Getter;

@Getter
public class CityNotFoundException extends RuntimeException{
    private String message = "City not found.";
    private final String debugMessage;

    public CityNotFoundException(Integer id) {
        message += " In database does not exists an city with id " + id + ".";
        debugMessage = "No city in database with that ID";
    }

    public CityNotFoundException(String name) {
        message += " In database does not exists an city with name " + name + ".";
        debugMessage = "No city in database with that name";
    }

    public CityNotFoundException(Integer id, String name) {
        message += " In database does not exists an city with id " + id + " and name " + name + ".";
        debugMessage = "No city in database with that ID and name";
    }
}
