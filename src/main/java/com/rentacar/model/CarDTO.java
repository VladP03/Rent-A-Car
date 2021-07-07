package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;

import javax.validation.constraints.*;

public class CarDTO {
    @Null(message = "Car's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "Car's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "Car's ID must be at least 1", groups = {OnUpdate.class})
    private Integer ID;

    @NotEmpty(message = "Car's brand name may not be null or empty")
    private String brandName;

    @NotEmpty(message = "Car's name may not be null or empty")
    private String name;

    @NotEmpty(message = "Car's VIN may not be null or empty")
    private String VIN;

    @NotNull(message = "Car's first registration may not be null")
    @Positive(message = "Car's first registration may not be negative or zero")
    private Integer firstRegistration;

    @NotNull(message = "Car's engine capacity may not be null")
    @Positive(message = "Car's engine capacity may not be negative or zero")
    private Integer engineCapacity;

    @NotEmpty(message = "Car's fuel may not be null or empty")
    private String fuel;

    @NotEmpty(message = "Car's km may not be null or empty")
    private String mileage;

    @NotEmpty(message = "Car's gearbox may not be null or empty")
    private String gearbox;

    public Integer getID() {
        return ID;
    }

    public CarDTO setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public CarDTO setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public String getName() {
        return name;
    }

    public CarDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getVIN() {
        return VIN;
    }

    public CarDTO setVIN(String VIN) {
        this.VIN = VIN;
        return this;
    }

    public Integer getFirstRegistration() {
        return firstRegistration;
    }

    public CarDTO setFirstRegistration(Integer firstRegistration) {
        this.firstRegistration = firstRegistration;
        return this;
    }
    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public CarDTO setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
        return this;
    }

    public String getFuel() {
        return fuel;
    }

    public CarDTO setFuel(String fuel) {
        this.fuel = fuel;
        return this;
    }

    public String getMileage() {
        return mileage;
    }

    public CarDTO setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getGearbox() {
        return gearbox;
    }

    public CarDTO setGearbox(String gearbox) {
        this.gearbox = gearbox;
        return this;
    }
}
