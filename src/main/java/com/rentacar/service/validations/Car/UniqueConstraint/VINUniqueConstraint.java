package com.rentacar.service.validations.Car.UniqueConstraint;

import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.dataIntegrity.VinUniqueConstraintException;

public class VINUniqueConstraint {

    public static void checkConstraint(String VIN, CarRepository carRepository) throws VinUniqueConstraintException{
        boolean isPresent = carRepository.findByVIN(VIN);

        if (isPresent) {
            throw new VinUniqueConstraintException();
        }
    }
}
