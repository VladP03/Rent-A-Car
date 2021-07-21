package com.rentacar.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

@Builder
@Getter @Setter
public class UserDTO {
    @Null(message = "User's ID must be null")
    private Integer ID;

    private String username;

    @NotEmpty(message = "User's password must be not empty")
    private String password;
}
