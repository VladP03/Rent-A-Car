package com.rentacar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@Getter @Setter
public class RentDTO {

    @Null(message = "Rent's id must be null", groups = {OnCreate.class})
    @NotNull(message = "Rent's id must be not null", groups = {OnUpdate.class})
    @Min(value = 1, groups = {OnUpdate.class})
    private Integer ID;

    @NotNull(message = "Rent's car must be not null")
    private CarDTO car;

    @NotNull(message = "Rent's start date must be not null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private String startDate;

    @NotNull(message = "Rent's end date must be not null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private String endDate;
}
