package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.service.exceptions.car.BusinessLogic.CarGearboxException;

import java.util.ArrayList;
import java.util.List;

public class GearBox implements Chain {

    private final String carGearBox;
    private Chain nextInChain;


    public GearBox(String carGearBox) {
        this.carGearBox = carGearBox;
    }


    @Override
    public void setNextChain(Chain nextChain) {
        nextInChain = nextChain;
    }

    @Override
    public void execute() {
        List<String> gearBoxType = new ArrayList<>();

        gearBoxType.add("MANUAL");
        gearBoxType.add("AUTOMATIC");

        if (!gearBoxType.contains(carGearBox.toUpperCase())) {
            throw new CarGearboxException();
        }

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
