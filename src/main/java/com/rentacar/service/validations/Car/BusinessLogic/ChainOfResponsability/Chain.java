package com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability;

import com.rentacar.model.CarDTO;

public interface Chain {
    void setNextChain(Chain nextChain);
    void execute(CarDTO carDTO);
}
