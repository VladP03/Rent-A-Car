package com.rentacar.service.exceptions.dealership;

public class DealershipNotFoundException extends RuntimeException{

    private final Integer id;
    private String message = "Dealership not found.";
    private String debugMessage = "No dealership in database with that ID";

    public DealershipNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        message += " In database does not exists an dealership with id " + id + ".";

        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
