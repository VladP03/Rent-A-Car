package com.rentacar.service.validations.Car.BusinessLogic;

import com.rentacar.model.CarDTO;
import com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability.ChainOfResponsability;

public class CarBusinessLogic {

    public void validateBusinessLogic(CarDTO carDTO) {
        new ChainOfResponsability().returnFirstChain().execute(carDTO);
    }
}
