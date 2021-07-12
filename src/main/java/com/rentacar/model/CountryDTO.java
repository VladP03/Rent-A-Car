package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Builder
@Getter @Setter
public class CountryDTO {
    @Null(message = "Country's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "Country's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "Country's ID must be at least 1", groups = {OnUpdate.class})
    private Integer id;

    @NotEmpty(message = "Country's name can not be null or empty")
    private String name;

    @NotEmpty(message = "Country's phone number can not be null or empty")
    private String phoneNumber;

    private List<CityDTO> cityList;
}
