package com.rentacar.repository.dealership;

import com.rentacar.repository.car.Car;

import javax.persistence.*;
import java.util.*;

@Entity
@SequenceGenerator(name = "DealershipSeq", allocationSize = 1500)
public class Dealership {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DealershipSeq")
    private Integer ID;

    private String name;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;

    @OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="DEALER_ID") // join column is in table for Order
    private List<Car> cars = new ArrayList<>();

    public Dealership() {

    }

    public Integer getID() {
        return ID;
    }

    public Dealership setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public String getName() {
        return name;
    }

    public Dealership setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Dealership setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Dealership setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Dealership setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Dealership setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<Car> getCars() {
        return cars;
    }

    public Dealership setCars(List<Car> cars) {
        this.cars = cars;
        return this;
    }
}
