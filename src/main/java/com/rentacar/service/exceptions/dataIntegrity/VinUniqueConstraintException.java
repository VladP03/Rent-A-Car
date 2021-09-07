package com.rentacar.service.exceptions.dataIntegrity;

import com.rentacar.model.CarDTO;
import lombok.Getter;

@Getter
public class VinUniqueConstraintException extends RuntimeException {
    private String message;
    private String debugMessage;

    public VinUniqueConstraintException() {}

    public VinUniqueConstraintException(CarDTO carDTO) {
        message = "VIN unique constraint violated on Car, name: " + carDTO.getBrandName() + ", " + carDTO.getName() + ", VIN: " + carDTO.getVIN() + " already exists.";
        debugMessage = "Change VIN";
    }
}
