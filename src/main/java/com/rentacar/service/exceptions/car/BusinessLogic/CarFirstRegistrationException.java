package com.rentacar.service.exceptions.car.BusinessLogic;

import com.rentacar.model.CarDTO;
import com.rentacar.service.validations.Car.BusinessLogic.ChainOfResponsability.FirstRegistration;
import lombok.Getter;

@Getter
public class CarFirstRegistrationException extends CarBusinessLogicException {
    private String message = "Car first registration can not be ";
    private String debugMessage;


    public CarFirstRegistrationException() {}

    public CarFirstRegistrationException(CarDTO carDTO) {
        olderOrGreater(carDTO.getFirstRegistration());

        message += " Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".";
        debugMessage = "Car's first registration must be between " + FirstRegistration.MIN_YEAR + " and " + FirstRegistration.MAX_YEAR;
    }

    private void olderOrGreater(Integer firstRegistration) {
        if (firstRegistration > FirstRegistration.MAX_YEAR) {
            message += "greater than current year, year introduced: " + firstRegistration + ".";
        } else if (firstRegistration < FirstRegistration.MIN_YEAR) {
            message += "older than 10 years, year introduced: " + firstRegistration + ".";
        }
    }
}
