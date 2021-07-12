package com.rentacar.service;

import com.rentacar.model.CityDTO;
import com.rentacar.model.CountryDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.model.adapters.CountryAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.country.Country;
import com.rentacar.repository.country.CountryRepository;
import com.rentacar.service.exceptions.country.CountryAlreadyExistsException;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.*;

@Component
@Validated
public class CountryService {

    private final CountryRepository countryRepository;
    private final CityService cityService;

    public CountryService(CountryRepository countryRepository, CityService cityService) {
        this.countryRepository = countryRepository;
        this.cityService = cityService;
    }

    public List<CountryDTO> getCountry(Integer id, String name) {
        if (id == null && name == null) {
            return CountryAdapter.toListDTO(countryRepository.findAll());
        } else if (id != null && name != null) {
            Optional<List<Country>> countryFounded = countryRepository.findByIdAndName(id, name);

            if (countryFounded.isPresent()) {
                return CountryAdapter.toListDTO(countryFounded.get());
            } else {
                throw  new CountryNotFoundException(id, name);
            }
        } else {
            Optional<List<Country>> countryFounded = countryRepository.findByIdOrName(id, name);

            if (countryFounded.isPresent()) {
                return CountryAdapter.toListDTO(countryFounded.get());
            } else if (name == null){
               throw new CountryNotFoundException(id);
            } else {
                throw new CountryNotFoundException(name);
            }
        }
    }

    @Validated(OnCreate.class)
    public CountryDTO createCountry(@Valid CountryDTO countryDTO) {
        nameToUpper(countryDTO);

        Optional<Country> countryFoundedByName = countryRepository.findByName(countryDTO.getName());
        Optional<Country> countryFoundedByPhoneNumber = countryRepository.findByPhoneNumber(countryDTO.getPhoneNumber());

        if (!countryFoundedByName.isPresent() && !countryFoundedByPhoneNumber.isPresent()) {
            for (CityDTO cityDTO : countryDTO.getCityList()) {
                cityService.createCity(cityDTO);
            }

            return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));

        } else if (countryFoundedByName.isPresent() && countryFoundedByPhoneNumber.isPresent()) {
            throw new CountryAlreadyExistsException(countryDTO, "name", "phone number");
        } else if (countryFoundedByName.isPresent()) {
            throw new CountryAlreadyExistsException(countryDTO, "name");
        } else {
            throw new CountryAlreadyExistsException(countryDTO, "phone number");
        }
    }

    @Validated(OnUpdate.class)
    public CountryDTO updateCountry(@Valid CountryDTO countryDTO) {
        nameToUpper(countryDTO);

        Optional<Country> countryFoundedById = countryRepository.findById(countryDTO.getId());
        Optional<Country> countryFoundedByName = countryRepository.findByName(countryDTO.getName());

        if (countryFoundedById.isPresent() && !countryFoundedByName.isPresent()) {
            for (CityDTO cityDTO : countryDTO.getCityList()) {
                cityService.createCity(cityDTO);
            }

            return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));
        } else if (!countryFoundedById.isPresent()) {
            throw new CountryNotFoundException(countryDTO.getId());
        } else {
            throw new CountryAlreadyExistsException(countryDTO);
        }
    }

    @Validated(OnUpdate.class)
    public CountryDTO patchCountry(CountryDTO countryDTO) {
        //TODO
        return null;
    }

    public CountryDTO deleteCountry(Integer id) {

        Optional<Country> countryFounded = countryRepository.findById(id);

        if (countryFounded.isPresent()) {
            countryRepository.deleteById(id);

            return CountryAdapter.toDTO(countryFounded.get());
        } else {
            throw new CountryNotFoundException(id);
        }
    }

    private void nameToUpper(CountryDTO countryDTO) {
        countryDTO.setName(countryDTO.getName().toUpperCase());
    }
}
