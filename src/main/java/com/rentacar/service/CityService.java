package com.rentacar.service;

import com.rentacar.model.CityDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.city.City;
import com.rentacar.repository.city.CityRepository;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.NameUniqueConstraintException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.*;

@Component
@Validated
public class CityService {

    private final CityRepository cityRepository;



    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }



    public List<CityDTO> getListOfCities(Integer id, String name) {
        if (id == null && name == null) {
            return CityAdapter.toListDTO(cityRepository.findAll());
        }

        if (id != null && name != null) {
            // all cities name in database are in upper case
            name = name.toUpperCase();
            return getCity(id, name);
        }

        if (id == null) {
            // all cities name in database are in upper case
            name = name.toUpperCase();
            return getCity(name);
        }

        return getCity(id);
    }

    @Validated(OnCreate.class)
    public void createCity(@Valid CityDTO cityDTO) {
        stringVariablesToUpper(cityDTO);
    }

    @Validated(OnUpdate.class)
    public void updateCity(@Valid CityDTO cityDTO) {
        checkIfExistsAnCityWithThisId(cityDTO.getId());
        stringVariablesToUpper(cityDTO);
    }

    public void deleteCity(Integer id) {
        checkIfExistsAnCityWithThisId(id);
    }


    // Admin

    @Validated(OnCreate.class)
    public CityDTO createCityAdmin(@Valid CityDTO cityDTO) {
        stringVariablesToUpper(cityDTO);
        checkIfCityNameIsUnique(cityDTO);

        return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
    }

    @Validated(OnUpdate.class)
    public CityDTO updateCityAdmin(@Valid CityDTO cityDTO) {
        checkIfExistsAnCityWithThisId(cityDTO.getId());
        stringVariablesToUpper(cityDTO);
        checkIfCityNameIsUnique(cityDTO);

        return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
    }

    public CityDTO deleteCityAdmin(Integer id) {
        CityDTO cityThatWilLBeDeleted = getCityEligibleForDelete(id);
        cityRepository.delete(CityAdapter.fromDTO(cityThatWilLBeDeleted));

        return cityThatWilLBeDeleted;
    }


    // Small functions

    private List<CityDTO> getCity(Integer id, String name) {
        Optional<City> cityFounded = cityRepository.findByIdAndName(id, name);

        if (cityFounded.isPresent()) {
            return CityAdapter.toListDTO(Collections.singletonList(cityFounded.get()));
        } else {
            throw new CityNotFoundException(id, name);
        }
    }


    private List<CityDTO> getCity(Integer id) {
        Optional<City> cityFounded = cityRepository.findById(id);

        if (cityFounded.isPresent()) {
            return CityAdapter.toListDTO(Collections.singletonList(cityFounded.get()));
        } else {
            throw new CityNotFoundException(id);
        }
    }


    private List<CityDTO> getCity(String name) {
        Optional<City> cityFounded = cityRepository.findByName(name);

        if (cityFounded.isPresent()) {
            return CityAdapter.toListDTO(Collections.singletonList(cityFounded.get()));
        } else {
            throw new CityNotFoundException(name);
        }
    }


    private CityDTO getCityEligibleForDelete(Integer id) {
        Optional<City> cityFounded = cityRepository.findById(id);

        if (!cityFounded.isPresent()) {
            throw new CityNotFoundException(id);
        } else {
            return CityAdapter.toDTO(cityFounded.get());
        }
    }

    private void checkIfExistsAnCityWithThisId(Integer id) {
        Optional<City> cityFounded = cityRepository.findById(id);

        if (!cityFounded.isPresent()) {
            throw new CityNotFoundException(id);
        }
    }



    private void checkIfCityNameIsUnique(CityDTO cityDTO) {
        if (existsACityWithName(cityDTO.getName())) {
            throw new NameUniqueConstraintException(cityDTO);
        }
    }

    private boolean existsACityWithName(String name) {
        Optional<City> cityFounded = cityRepository.findByName(name);

        return cityFounded.isPresent();
    }



    // Make all City's String variables to Upper for a better look in database
    private void stringVariablesToUpper(CityDTO cityDTO) {
        cityDTO.setName(cityDTO.getName().toUpperCase());
    }
}
