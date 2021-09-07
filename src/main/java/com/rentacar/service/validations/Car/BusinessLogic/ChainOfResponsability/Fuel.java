package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.service.exceptions.car.BusinessLogic.CarFuelException;

import java.util.ArrayList;
import java.util.List;

public class Fuel implements Chain {

    private final String carFuel;
    private Chain nextInChain;


    public Fuel(String carFuel) {
        this.carFuel = carFuel;
    }


    @Override
    public void setNextChain(Chain nextChain) {
        nextInChain = nextChain;
    }

    @Override
    public void execute() {
        List<String> fuelType = new ArrayList<>();

        fuelType.add("GAS");
        fuelType.add("DIESEL");
        fuelType.add("HYBRID");
        fuelType.add("ELECTRIC");

        if (!fuelType.contains(carFuel.toUpperCase())) {
            throw new CarFuelException();
        }

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
