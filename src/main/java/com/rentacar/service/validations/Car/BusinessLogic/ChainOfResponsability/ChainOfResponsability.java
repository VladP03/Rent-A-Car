package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

public class ChainOfResponsability {

    private final Chain firstRegistration;
    private final Chain fuel;
    private final Chain gearBox;

    public ChainOfResponsability() {
        firstRegistration = new FirstRegistration();
        fuel = new Fuel();
        gearBox = new GearBox();

        firstRegistration.setNextChain(fuel);
        fuel.setNextChain(gearBox);
    }

    public Chain returnFirstChain() {
        return firstRegistration;
    }
}
