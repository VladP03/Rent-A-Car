package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.*;

@Getter
@Setter
public class DealershipDTO {
    @Null(message = "Dealership's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "Dealership's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "Dealership's ID must be at least 1", groups = {OnUpdate.class})
    private Integer ID;

    @NotEmpty(message = "Dealership's name may not be null or empty")
    private String name;

    @NotNull(message = "Dealership's city may not be null or empty")
    private CityDTO city;

    @NotNull(message = "Dealership's country may not be null or empty")
    private CountryDTO country;

    @Email(message = "Dealership's email invalid format")
    @NotNull(message = "Dealership's email may not be null")
    private String email;

    @NotNull(message = "Dealership's phone number may not be null")
    @Pattern(regexp = "^[0]\\d{9}")
    private String phoneNumber;

    private List<CarDTO> cars = new ArrayList<>();
}
