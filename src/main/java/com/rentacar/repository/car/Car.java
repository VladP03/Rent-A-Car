package com.rentacar.repository.car;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "CarSeq", allocationSize = 1500)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CarSeq")
    private Integer ID;

    private String brandName;
    private String name;
    private String VIN;
    private Integer firstRegistration;
    private Integer engineCapacity;
    private String fuel;
    private Double mileage;
    private String gearbox;


    public Car() {
    }

    public Integer getID() {
        return ID;
    }

    public Car setID(Integer ID) {
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

    public String getVIN() {
        return VIN;
    }

    public Car setVIN(String VIN) {
        this.VIN = VIN;
        return this;
    }

    public Integer getFirstRegistration() {
        return firstRegistration;
    }

    public Car setFirstRegistration(Integer firstRegistration) {
        this.firstRegistration = firstRegistration;
        return this;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public Car setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
        return this;
    }

    public String getFuel() {
        return fuel;
    }

    public Car setFuel(String fuel) {
        this.fuel = fuel;
        return this;
    }

    public Double getMileage() {
        return mileage;
    }

    public Car setMileage(Double mileage) {
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
