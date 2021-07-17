package com.rentacar.service.exceptions.dealership;

import com.rentacar.model.DealershipDTO;
import lombok.Getter;

@Getter
public class DealershipCityInvalidException extends RuntimeException{
    private final String message;
    private final String debugMessage;

    public DealershipCityInvalidException(DealershipDTO dealershipDTO) {
        message = "City: " + dealershipDTO.getCity().getName() + " does not exists in " + dealershipDTO.getCountry().getName();
        debugMessage = "Introduce a valid city";
    }
}
