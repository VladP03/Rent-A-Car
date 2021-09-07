package com.rentacar.service;

import com.rentacar.model.CityDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.city.City;
import com.rentacar.repository.city.CityRepository;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import com.rentacar.service.validations.City.CityValidation;
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
            return Collections.singletonList(getCity(name));
        }

        return Collections.singletonList(getCity(id));
    }


    @Validated(OnCreate.class)
    public void createCity(@Valid CityDTO cityDTO) {
        new CityValidation(cityDTO, cityRepository).validateCreate();
    }


    @Validated(OnUpdate.class)
    public void updateCity(@Valid CityDTO cityDTO) {
        new CityValidation(cityDTO, cityRepository).validateUpdate();
    }



    // Admin

    @Validated(OnCreate.class)
    public CityDTO createCityAdmin(@Valid CityDTO cityDTO) {
        createCity(cityDTO);
        return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
    }


    @Validated(OnUpdate.class)
    public CityDTO updateCityAdmin(@Valid CityDTO cityDTO) {
        updateCity(cityDTO);
        return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
    }


    @Validated(OnUpdate.class)
    public CityDTO deleteCityAdmin(@Valid CityDTO cityDTO) {
        return new CityValidation(cityDTO, cityRepository).validateDelete();
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


    private CityDTO getCity(Integer id) {
        Optional<City> cityFounded = cityRepository.findById(id);

        if (cityFounded.isPresent()) {
            return CityAdapter.toDTO(cityFounded.get());
        } else {
            throw new CityNotFoundException(id);
        }
    }


    private CityDTO getCity(String name) {
        Optional<City> cityFounded = cityRepository.findByName(name);

        if (cityFounded.isPresent()) {
            return CityAdapter.toDTO(cityFounded.get());
        } else {
            throw new CityNotFoundException(name);
        }
    }
}
