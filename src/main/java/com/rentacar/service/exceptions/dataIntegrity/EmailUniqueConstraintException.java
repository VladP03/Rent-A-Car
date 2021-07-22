package com.rentacar.service.exceptions.dataIntegrity;

import com.rentacar.model.DealershipDTO;
import com.rentacar.model.UserDTO;
import lombok.Getter;

@Getter
public class EmailUniqueConstraintException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public EmailUniqueConstraintException(DealershipDTO dealershipDTO) {
        this.message = "Email unique constraint violated on Dealership, email: " + dealershipDTO.getEmail() + " already exists.";
        debugMessage = "Change email";
    }

    public EmailUniqueConstraintException(UserDTO userDTO) {
        this.message = "Email unique constraint violated on User, email: " + userDTO.getEmail() + " already exists.";
        debugMessage = "Change email";
    }
}
