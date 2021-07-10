package com.rentacar.service;

import com.rentacar.model.CityDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.city.City;
import com.rentacar.repository.city.CityRepository;
import com.rentacar.service.exceptions.city.CityAlreadyExistsException;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Component
@Validated
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityDTO> getAllCountries() {
        return CityAdapter.toListDTO(cityRepository.findAll());
    }

    @Validated(OnCreate.class)
    public CityDTO createCity(CityDTO cityDTO) {
        nameToUpper(cityDTO);

        Optional<City> cityFounded = cityRepository.findByName(cityDTO.getName());

        if (!cityFounded.isPresent()) {

            return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
        } else {
            throw new CityAlreadyExistsException(CityAdapter.toDTO(cityFounded.get()));
        }
    }

    @Validated(OnUpdate.class)
    public CityDTO updateCity(CityDTO cityDTO) {
        nameToUpper(cityDTO);

        Optional<City> cityFoundedById = cityRepository.findById(cityDTO.getId());
        Optional<City> cityFoundedByName = cityRepository.findByName(cityDTO.getName());

        if (cityFoundedById.isPresent() && !cityFoundedByName.isPresent()) {
            nameToUpper(cityDTO);

            return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
        } else if (!cityFoundedById.isPresent()) {
            throw new CityNotFoundException(cityDTO.getId());
        } else {
            throw new CityAlreadyExistsException(CityAdapter.toDTO(cityFoundedByName.get()));
        }
    }

    public CityDTO deleteCity(Integer id) {

        Optional<City> cityFounded = cityRepository.findById(id);

        if (cityFounded.isPresent()) {
            cityRepository.deleteById(id);

            return CityAdapter.toDTO(cityFounded.get());
        } else {
            throw new CityNotFoundException(id);
        }
    }



    private void nameToUpper(CityDTO cityDTO) {
        cityDTO.setName(cityDTO.getName().toUpperCase());
    }
}
