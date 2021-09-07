package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.service.exceptions.car.BusinessLogic.CarFirstRegistrationException;

import java.util.Calendar;

public class FirstRegistration implements Chain {

    private final int carRegistration;

    public static final int MAX_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    public static final int MIN_YEAR = MAX_YEAR - 10;

    private Chain nextInChain;


    public FirstRegistration(int carRegistration) {
        this.carRegistration = carRegistration;
    }


    @Override
    public void setNextChain(Chain nextChain) {
        nextInChain = nextChain;
    }

    @Override
    public void execute() {
        if (carRegistration > MAX_YEAR) {
            throw new CarFirstRegistrationException();
        } else if (carRegistration < MIN_YEAR) {
            throw new CarFirstRegistrationException();
        }

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }


}
