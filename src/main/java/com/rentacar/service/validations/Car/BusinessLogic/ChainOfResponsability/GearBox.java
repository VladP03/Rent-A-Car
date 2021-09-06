package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.model.CarDTO;
import com.rentacar.service.exceptions.car.CarGearboxException;

import java.util.ArrayList;
import java.util.List;

class GearBox implements Chain {

    private Chain nextInChain;

    @Override
    public void setNextChain(Chain nextChain) {
        nextInChain = nextChain;
    }

    @Override
    public void execute(CarDTO carDTO) {
        List<String> gearBoxType = new ArrayList<>();

        gearBoxType.add("MANUAL");
        gearBoxType.add("AUTOMATIC");

        if (!gearBoxType.contains(carDTO.getGearbox().toUpperCase())) {
            throw new CarGearboxException(carDTO);
        }

        if (nextInChain != null) {
            nextInChain.execute(carDTO);
        }
    }
}
