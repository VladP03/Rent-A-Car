package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;

import javax.validation.constraints.*;
import java.util.*;

public class DealershipDTO {
    @Null(message = "Dealership's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "Dealership's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "Dealership's ID must be at least 1", groups = {OnUpdate.class})
    private Integer ID;

    @NotEmpty(message = "Dealership's name may not be null or empty")
    private String name;

    @NotEmpty(message = "Dealership's city may not be null or empty")
    private String city;

    @NotEmpty(message = "Dealership's country may not be null or empty")
    private String country;

    @Email(message = "Dealership's email invalid format")
    @NotNull(message = "Dealership's email may not be null")
    private String email;

    @NotNull(message = "Dealership's phone number can not be null")
    @Pattern(regexp = "^[+](40|49|43|33)\\d{9}", message = "Dealership's phone number invalid")
    private String phoneNumber;

    private List<CarDTO> cars = new ArrayList<>();

    public Integer getID() {
        return ID;
    }

    public DealershipDTO setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public String getName() {
        return name;
    }

    public DealershipDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public DealershipDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public DealershipDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DealershipDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public DealershipDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public DealershipDTO setCars(List<CarDTO> cars) {
        this.cars = cars;
        return this;
    }
}
