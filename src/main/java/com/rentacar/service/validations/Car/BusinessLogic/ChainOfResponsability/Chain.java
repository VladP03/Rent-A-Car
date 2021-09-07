package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

public interface Chain {
    void setNextChain(Chain nextChain);
    void execute();
}
