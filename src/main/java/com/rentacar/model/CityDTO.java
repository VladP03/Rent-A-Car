package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class CityDTO {
    @Null(message = "City's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "City's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "City's ID must be at least 1", groups = {OnUpdate.class})
    private Integer id;

    @NotEmpty(message = "City's name can not be null or empty")
    private String name;


    public Integer getId() {
        return id;
    }

    public CityDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityDTO setName(String name) {
        this.name = name;
        return this;
    }
}
