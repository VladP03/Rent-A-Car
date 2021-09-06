package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.model.CarDTO;
import com.rentacar.service.exceptions.car.CarFuelException;

import java.util.ArrayList;
import java.util.List;

class Fuel implements Chain {

    private Chain nextInChain;

    @Override
    public void setNextChain(Chain nextChain) {
        nextInChain = nextChain;
    }

    @Override
    public void execute(CarDTO carDTO) {
        List<String> fuelType = new ArrayList<>();

        fuelType.add("GAS");
        fuelType.add("DIESEL");
        fuelType.add("HYBRID");
        fuelType.add("ELECTRIC");

        if (!fuelType.contains(carDTO.getFuel().toUpperCase())) {
            throw new CarFuelException(carDTO);
        }

        if (nextInChain != null) {
            nextInChain.execute(carDTO);
        }
    }
}
