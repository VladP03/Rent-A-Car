package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;

import java.util.Calendar;

public class CarFirstRegistrationException extends RuntimeException{
    private final CarDTO carDTO;
    private String message = "Car first registration can not be ";

    public CarFirstRegistrationException(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    @Override
    public String getMessage() {
        if (carDTO.getFirstRegistration() > Calendar.getInstance().get(Calendar.YEAR)) {
            message += "greater than current year, year introduced: " + carDTO.getFirstRegistration() + ".";
        } else if (carDTO.getFirstRegistration() < Calendar.getInstance().get(Calendar.YEAR) - 10) {
            message += "older than 10 years, year introduced: " + carDTO.getFirstRegistration() + ".";
        }

        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";

        return message;
    }
}
