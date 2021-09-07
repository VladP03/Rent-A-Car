package com.rentacar.service.validations.City;

import com.rentacar.model.CityDTO;
import com.rentacar.repository.city.City;
import com.rentacar.repository.city.CityRepository;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.NameUniqueConstraintException;

import java.util.Optional;

public class CityValidation {

    private final CityDTO cityDTO;
    private final CityRepository cityRepository;



    public CityValidation(CityDTO cityDTO, CityRepository cityRepository) {
        this.cityDTO = cityDTO;
        this.cityRepository = cityRepository;

        stringVariablesToUpper();
    }



    public void validateCreate() {
        checkIfNameIsUnique();
    }


    public void validateUpdate() {
        checkIfIDExists();
        checkIfNameIsUnique();
    }


    public CityDTO validateDelete() {
        cityRepository.deleteById(cityDTO.getId());
        return cityDTO;
    }



    // Small functions

    private void checkIfNameIsUnique() {
        Optional<City> cityFounded = cityRepository.findByName(cityDTO.getName());

        if (cityFounded.isPresent()) {
            throw new NameUniqueConstraintException(cityDTO);
        }
    }


    private void checkIfIDExists() {
        Optional<City> cityFounded = cityRepository.findById(cityDTO.getId());

        if (!cityFounded.isPresent()) {
            throw new CityNotFoundException(cityDTO.getId());
        }
    }



    // Make all City's String variables to Upper for a better look in database
    private void stringVariablesToUpper() {
        cityDTO.setName(cityDTO.getName().toUpperCase());
    }
}
