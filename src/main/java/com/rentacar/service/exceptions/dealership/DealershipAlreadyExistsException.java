package com.rentacar.service.exceptions.dealership;

import com.rentacar.model.DealershipDTO;

public class DealershipAlreadyExistsException extends RuntimeException{
    private String message;
    private String debugMessage;

    public DealershipAlreadyExistsException(DealershipDTO dealershipDTO, String... parameters) {
        message = "Dealership already exists. Error on the following country: " + dealershipDTO.getName();

        if (parameters.length > 0) {
            if (parameters.length == 1) {
                debugMessage = "Be careful at these parameter: " + parameters[0];
            } else {
                debugMessage = "Be careful at these parameters: ";
                for (int i=0;i<parameters.length-1;i++) {
                    debugMessage += parameters[i] + ", ";
                }
                debugMessage += parameters[parameters.length-1] + ".";
            }
        } else {
            debugMessage = "";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
