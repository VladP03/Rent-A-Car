package com.rentacar.service;

import com.rentacar.model.CityDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.city.City;
import com.rentacar.repository.city.CityRepository;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.NameUniqueConstraintException;
import org.springframework.dao.DataIntegrityViolationException;
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

    public List<CityDTO> getCity(Integer id, String name) {
        if (id == null && name == null) {
            return CityAdapter.toListDTO(cityRepository.findAll());

        } else if (id != null && name != null) {

            name = name.toUpperCase();
            Optional<City> cityFounded = cityRepository.findByIdAndName(id, name);

            if (cityFounded.isPresent()) {
                return CityAdapter.toListDTO(Collections.singletonList(cityFounded.get()));
            } else {
                throw  new CityNotFoundException(id, name);
            }
        } else if (name != null){

            name = name.toUpperCase();
            Optional<City> cityFounded = cityRepository.findByName(name);

            if (cityFounded.isPresent()) {
                return CityAdapter.toListDTO(Collections.singletonList(cityFounded.get()));
            } else {
                throw new CityNotFoundException(name);
            }
        } else {

            Optional<City> cityFounded = cityRepository.findById(id);

            if (cityFounded.isPresent()) {
                return CityAdapter.toListDTO(Collections.singletonList(cityFounded.get()));
            } else {
                throw new CityNotFoundException(id);
            }
        }
    }

    @Validated(OnCreate.class)
    public void createCity(@Valid CityDTO cityDTO) {
        nameToUpper(cityDTO);
    }

    @Validated(OnUpdate.class)
    public void updateCity(@Valid CityDTO cityDTO) {
        if (!existsCityById(cityDTO.getId())) {
            throw new CityNotFoundException(cityDTO.getId());
        }

        nameToUpper(cityDTO);
    }

    public void deleteCity(Integer id) {
        if (!existsCityById(id)) {
            throw new CityNotFoundException(id);
        }
    }


    // Admin

    @Validated(OnCreate.class)
    public CityDTO createCityAdmin(@Valid CityDTO cityDTO) {
        nameToUpper(cityDTO);

        try {
            return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
        } catch (DataIntegrityViolationException exception) {
            throw new NameUniqueConstraintException(cityDTO);
        }
    }

    @Validated(OnUpdate.class)
    public CityDTO updateCityAdmin(@Valid CityDTO cityDTO) {

        if (existsCityById(cityDTO.getId())) {
            nameToUpper(cityDTO);

            try {
                return CityAdapter.toDTO(cityRepository.save(CityAdapter.fromDTO(cityDTO)));
            } catch (DataIntegrityViolationException exception) {
                throw new NameUniqueConstraintException(cityDTO);
            }
        } else {
            throw new CityNotFoundException(cityDTO.getId());
        }
    }

    public CityDTO deleteCityAdmin(Integer id) {

        Optional<City> cityFounded = cityRepository.findById(id);

        if (cityFounded.isPresent()) {
            cityRepository.deleteById(id);

            return CityAdapter.toDTO(cityFounded.get());
        } else {
            throw new CityNotFoundException(id);
        }
    }



    private boolean existsCityById(Integer id) {
        Optional<City> cityFounded = cityRepository.findById(id);

        return cityFounded.isPresent();
    }

    protected boolean isNameUnique(String name) {
        Optional<City> cityFounded = cityRepository.findByName(name);

        return !cityFounded.isPresent();
    }



    private void nameToUpper(CityDTO cityDTO) {
        cityDTO.setName(cityDTO.getName().toUpperCase());
    }
}
