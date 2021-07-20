package com.rentacar.service.exceptions.rent;

import com.rentacar.model.RentDTO;
import lombok.Getter;

@Getter
public class RentCarIndisponibleException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public RentCarIndisponibleException(RentDTO rentDTO) {
        message = "The car: " + rentDTO.getCar().getBrandName() + " " + rentDTO.getCar().getName() + " with VIN: " + rentDTO.getCar().getVIN() + " is indisponible between " + rentDTO.getStartDate() + " - " + rentDTO.getEndDate() ;
        debugMessage = "Change dates";
    }
}
