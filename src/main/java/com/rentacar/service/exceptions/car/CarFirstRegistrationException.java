package com.rentacar.service.exceptions.car;

import com.rentacar.model.CarDTO;
import lombok.Getter;

import java.util.Calendar;

@Getter
public class CarFirstRegistrationException extends RuntimeException{
    private String message = "Car first registration can not be ";
    private final String debugMessage;

    private final int maxYear = Calendar.getInstance().get(Calendar.YEAR);
    private final int minYear = maxYear - 10;

    public CarFirstRegistrationException(CarDTO carDTO) {
        olderOrGreater(carDTO.getFirstRegistration());

        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";
        debugMessage = "Car's first registration must be between " + minYear + " and " + maxYear;
    }

    private void olderOrGreater(Integer firstRegistration) {
        if (firstRegistration > maxYear) {
            message += "greater than current year, year introduced: " + firstRegistration + ".";
        } else if (firstRegistration < minYear) {
            message += "older than 10 years, year introduced: " + firstRegistration + ".";
        }
    }
}
