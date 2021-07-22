package com.rentacar.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Builder
@Getter @Setter
public class UserDTO {
    @Null(message = "User's ID must be null")
    private Integer ID;

    private String username;

    @Email(message = "User email invalid")
    private String email;

    @NotEmpty(message = "User's password must be not empty")
    // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;
}

// Link for pattern: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a