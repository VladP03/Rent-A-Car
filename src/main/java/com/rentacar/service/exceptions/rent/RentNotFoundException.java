package com.rentacar.service.exceptions.rent;

import lombok.Getter;

@Getter
public class RentNotFoundException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public RentNotFoundException(Integer id) {
        message = "In database does not exists an rent with id " + id;
        debugMessage = "Change id";
    }
}
