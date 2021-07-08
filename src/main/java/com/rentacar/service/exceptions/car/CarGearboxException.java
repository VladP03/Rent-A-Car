package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;

public class CarGearboxException extends RuntimeException{
    private final CarDTO carDTO;
    private String message = "Car gearbox is incorrect.";
    private String debugMessage = "Car gearbox might be only: manual or automatic";

    public CarGearboxException(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    @Override
    public String getMessage() {
        message += " Gearbox introduced: " + carDTO.getGearbox() + ".";
        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
