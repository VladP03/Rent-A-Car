package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.*;

@Builder
@Getter @Setter
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

    private List<CarDTO> cars;
}
