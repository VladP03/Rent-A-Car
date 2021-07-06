package com.rentacar.repository.car;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    private String brandName;
    private String name;
    private Integer firstRegistration;
    private Double engineCapacity;
    private Integer horsePower;
    private String fuel;
    private String mileage;
    private String gearbox;
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
