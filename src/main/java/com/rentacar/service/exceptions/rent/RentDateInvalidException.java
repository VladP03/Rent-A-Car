package com.rentacar.service.exceptions.rent;

import lombok.Getter;

@Getter
public class RentDateInvalidException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public RentDateInvalidException(String startDate, String endDate) {
        message = "Start date is " + startDate + " and can not be after end date which is " + endDate ;
        debugMessage = "Change dates";
    }
}
