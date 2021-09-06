package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.model.CarDTO;
import com.rentacar.service.exceptions.car.CarFirstRegistrationException;

import java.util.Calendar;

class FirstRegistration implements Chain {

    private Chain nextInChain;

    @Override
    public void setNextChain(Chain nextChain) {
        nextInChain = nextChain;
    }

    @Override
    public void execute(CarDTO carDTO) {
        if (carDTO.getFirstRegistration() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new CarFirstRegistrationException(carDTO);
        } else if (carDTO.getFirstRegistration() < Calendar.getInstance().get(Calendar.YEAR) - 10) {
            throw new CarFirstRegistrationException(carDTO);
        }

        if (nextInChain != null) {
            nextInChain.execute(carDTO);
        }
    }
}
