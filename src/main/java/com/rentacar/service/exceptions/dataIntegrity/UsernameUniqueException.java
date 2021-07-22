package com.rentacar.service.exceptions.dataIntegrity;

import com.rentacar.model.UserDTO;
import lombok.Getter;

@Getter
public class UsernameUniqueException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public UsernameUniqueException(UserDTO userDTO) {
        this.message = "Username unique constraint violated on User, username: " + userDTO.getUsername() + " already exists.";
        debugMessage = "Change username";
    }
}
