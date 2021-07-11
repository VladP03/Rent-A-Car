package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;

public class CarGearboxException extends RuntimeException{
    private String message = "Car gearbox is incorrect.";
    private final String debugMessage;

    public CarGearboxException(CarDTO carDTO) {
        message += " Gearbox introduced: " + carDTO.getGearbox() + ".";
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        debugMessage = "Car gearbox might be only: manual or automatic";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
