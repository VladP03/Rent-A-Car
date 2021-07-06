package com.rentacar.repository.car;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @NotEmpty(message = "Car's brand name may not be null or empty")
    private String brandName;

    @NotEmpty(message = "Car's name may not be null or empty")
    private String name;

    @NotNull(message = "Car's first registration may not be null")
    @Positive(message = "Car's first registration may not be negative or zero")
    private Integer firstRegistration;

    @NotNull(message = "Car's engine capacity may not be null")
    @Positive(message = "Car's engine capacity may not be negative or zero")
    private Double engineCapacity;

    @NotNull(message = "Car's power may not be null")
    @Positive(message = "Car's power may not be negative or zero")
    private Integer horsePower;

    @NotEmpty(message = "Car's fuel may not be null or empty")
    private String fuel;

    @NotEmpty(message = "Car's km may not be null or empty")
    private String mileage;

    @NotEmpty(message = "Car's gearbox may not be null or empty")
    private String gearbox;

//    @NotNull(message = "Car's availability may not be null")
//    private Boolean available;


    public Car() {
    }


    public int getID() {
        return ID;
    }

    public Car setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public Car setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Car setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getFirstRegistration() {
        return firstRegistration;
    }

    public Car setFirstRegistration(Integer firstRegistration) {
        this.firstRegistration = firstRegistration;
        return this;
    }

    public Double getEngineCapacity() {
        return engineCapacity;
    }

    public Car setEngineCapacity(Double engineCapacity) {
        this.engineCapacity = engineCapacity;
        return this;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public Car setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public String getFuel() {
        return fuel;
    }

    public Car setFuel(String fuel) {
        this.fuel = fuel;
        return this;
    }

    public String getMileage() {
        return mileage;
    }

    public Car setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    public String getGearbox() {
        return gearbox;
    }

    public Car setGearbox(String gearbox) {
        this.gearbox = gearbox;
        return this;
    }

//    public Boolean getAvailable() {
//        return available;
//    }
//
//    public Car setAvailable(Boolean available) {
//        this.available = available;
//        return this;
//    }
}
