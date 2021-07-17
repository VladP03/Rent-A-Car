package com.rentacar.service.exceptions.dealership;

import lombok.Getter;

@Getter
public class DealershipNotFoundException extends RuntimeException{
    private String message = "Dealership not found.";
    private final String debugMessage;

    public DealershipNotFoundException(Integer id) {
        message += " In database does not exists an dealership with id " + id + ".";
        debugMessage = "No dealership in database with that ID";
    }

    public DealershipNotFoundException(String name) {
        message += " In database does not exists an dealership with name " + name + ".";
        debugMessage = "No dealership in database with that name";
    }

    public DealershipNotFoundException(Integer id, String name) {
        message += " In database does not exists an dealership with id " + id + " and name " + name + ".";
        debugMessage = "No dealership in database with that ID and name";
    }
}
