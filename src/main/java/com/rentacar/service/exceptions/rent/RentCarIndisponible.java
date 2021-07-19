package com.rentacar.service.exceptions.rent;

import com.rentacar.model.RentDTO;
import lombok.Getter;

@Getter
public class RentCarIndisponible extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public RentCarIndisponible(RentDTO rentDTO) {
        message = "The car: " + rentDTO.getCar().getBrandName() + " " + rentDTO.getCar().getName() + " with VIN: " + rentDTO.getCar().getVIN() + " is indisponible between " + rentDTO.getStartDate() + " - " + rentDTO.getEndDate() ;
        debugMessage = "Change dates";
    }
}
