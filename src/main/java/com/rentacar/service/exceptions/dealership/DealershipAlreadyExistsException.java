package com.rentacar.service.exceptions.dealership;

import com.rentacar.model.DealershipDTO;

public class DealershipAlreadyExistsException extends RuntimeException{
    private final DealershipDTO dealershipDTO;
    private String message = "Dealership already exists.";
    private final String debugMessage = "Change name, country, city, email or phone number";

    public DealershipAlreadyExistsException(DealershipDTO dealershipDTO) {
        this.dealershipDTO = dealershipDTO;
    }

    @Override
    public String getMessage() {
        message += " Error on the following dealership: " + dealershipDTO.getName() + " from " + dealershipDTO.getCountry() + ", " + dealershipDTO.getCity() +
                    " with email: " + dealershipDTO.getEmail() + " and phone number: " + dealershipDTO.getPhoneNumber();

        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
