package com.rentacar.service.exceptions.dataIntegrity;

import com.rentacar.model.CityDTO;
import com.rentacar.model.CountryDTO;
import lombok.Getter;

@Getter
public class NameUniqueConstraintException extends RuntimeException {
    private final String message;
    private final String debugMessage;

    public NameUniqueConstraintException(CityDTO cityDTO) {
        this.message = "Name unique constraint violated on City, name: " + cityDTO.getName() + " already exists.";
        debugMessage = "Change name";
    }

    public NameUniqueConstraintException(CountryDTO countryDTO) {
        this.message = "Name unique constraint violated on Country, name: " + countryDTO.getName() + " already exists.";
        debugMessage = "Change name";
    }
}
