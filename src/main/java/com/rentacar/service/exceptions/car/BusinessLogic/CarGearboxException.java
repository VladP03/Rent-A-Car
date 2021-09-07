package com.rentacar.service.exceptions.car.BusinessLogic;

import com.rentacar.model.CarDTO;
import lombok.Getter;

@Getter
public class CarGearboxException extends CarBusinessLogicException {
    private String message = "Car gearbox is incorrect.";
    private String debugMessage;


    public CarGearboxException() {}

    public CarGearboxException(CarDTO carDTO) {
        message += " Gearbox introduced: " + carDTO.getGearbox() + ".";
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        debugMessage = "Car gearbox might be only: manual or automatic";
    }
}
