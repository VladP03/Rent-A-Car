package com.rentacar.service.validations.Car.BusinessLogic;

import com.rentacar.model.CarDTO;
import com.rentacar.service.exceptions.car.BusinessLogic.CarBusinessLogicException;
import com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability.ChainOfResponsibility;

import java.lang.reflect.InvocationTargetException;

public class CarBusinessLogic {

    public static void validateBusinessLogic(CarDTO carDTO) {
        try {
            new ChainOfResponsibility(carDTO).returnFirstChain().execute();
        } catch (CarBusinessLogicException exception) {
            try {
                throw exception.getClass().getConstructor(CarDTO.class).newInstance(carDTO);
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ignored) {}
        }
    }
}
