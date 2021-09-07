package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.model.CarDTO;

public class ChainOfResponsibility {

    private final Chain firstRegistration;


    public ChainOfResponsibility(CarDTO carDTO) {
        firstRegistration = new FirstRegistration(carDTO.getFirstRegistration());

        Chain fuel = new Fuel(carDTO.getFuel());
        Chain gearBox = new GearBox(carDTO.getGearbox());

        firstRegistration.setNextChain(fuel);
        fuel.setNextChain(gearBox);
    }


    public Chain returnFirstChain() {
        return firstRegistration;
    }
}
