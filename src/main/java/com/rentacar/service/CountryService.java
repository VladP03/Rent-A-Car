package com.rentacar.service;

import com.rentacar.model.CityDTO;
import com.rentacar.model.CountryDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.model.adapters.CountryAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.country.Country;
import com.rentacar.repository.country.CountryRepository;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.NameUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.PhoneNumberUniqueConstraintException;
import org.springframework.dao.DataIntegrityViolationException;
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

            name = name.toUpperCase();
            Optional<Country> countryFounded = countryRepository.findByIdAndName(id, name);

            if (countryFounded.isPresent()) {
                return CountryAdapter.toListDTO(Collections.singletonList(countryFounded.get()));
            } else {
                throw new CountryNotFoundException(id, name);
            }
        } else if (name != null){

            name = name.toUpperCase();
            Optional<Country> countryFounded = countryRepository.findByName(name);

            if (countryFounded.isPresent()) {
                return CountryAdapter.toListDTO(Collections.singletonList(countryFounded.get()));
            } else {
                throw new CountryNotFoundException(name);
            }
        } else {

            Optional<Country> countryFounded = countryRepository.findById(id);

            if (countryFounded.isPresent()) {
                return CountryAdapter.toListDTO(Collections.singletonList(countryFounded.get()));
            } else {
                throw new CountryNotFoundException(id);
            }
        }
    }

    @Validated(OnCreate.class)
    public CountryDTO createCountry(@Valid CountryDTO countryDTO) {
        CountryDTO countryCreated = null;

        nameToUpper(countryDTO);

        for (CityDTO cityDTO : countryDTO.getCityList()) {
            cityService.createCity(cityDTO);
        }

        try {
            countryCreated = CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));
        } catch (DataIntegrityViolationException exception) {

            if (!isUniqueCountryName(countryDTO.getName())) {
                throw new NameUniqueConstraintException(countryDTO);
            }

            if (!isUniquePhoneNumber(countryDTO.getPhoneNumber())) {
                throw new PhoneNumberUniqueConstraintException(countryDTO);
            }

            for (CityDTO cityDTO : countryDTO.getCityList()) {
                if (!cityService.isNameUnique(cityDTO.getName())) {
                    throw new NameUniqueConstraintException(cityDTO);
                }
            }
        }

        return countryCreated;
    }

    @Validated(OnUpdate.class)
    public CountryDTO updateCountry(@Valid CountryDTO countryDTO) {

        if (existsID(countryDTO.getId())) {
            CountryDTO countryUpdated = null;

            nameToUpper(countryDTO);

            for (CityDTO cityDTO : countryDTO.getCityList()) {
                cityService.createCity(cityDTO);
            }

            try {
                countryUpdated =  CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));
            } catch (DataIntegrityViolationException exception) {

                if (!isUniqueCountryName(countryDTO.getName())) {
                    throw new NameUniqueConstraintException(countryDTO);
                }

                if (!isUniquePhoneNumber(countryDTO.getPhoneNumber())) {
                    throw new PhoneNumberUniqueConstraintException(countryDTO);
                }

                for (CityDTO cityDTO : countryDTO.getCityList()) {
                    if (!cityService.isNameUnique(cityDTO.getName())) {
                        throw new NameUniqueConstraintException(cityDTO);
                    }
                }
            }

            return countryUpdated;
        } else {
            throw new CountryNotFoundException(countryDTO.getId());
        }
    }

    @Validated(OnUpdate.class)
    public CountryDTO patchCountry(CountryDTO countryDTO) {

        Optional<Country> countryFoundedById = countryRepository.findById(countryDTO.getId());

        if (countryFoundedById.isPresent()) {
            if (countryDTO.getName() != null) {
                nameToUpper(countryDTO);

                countryFoundedById.get().setName(countryDTO.getName());

                try {
                    countryRepository.save(countryFoundedById.get());
                } catch (DataIntegrityViolationException exception) {
                    throw new NameUniqueConstraintException(countryDTO);
                }
            }

            if (countryDTO.getPhoneNumber() != null) {
                countryFoundedById.get().setPhoneNumber(countryDTO.getPhoneNumber());

                try {
                    countryRepository.save(countryFoundedById.get());
                } catch (DataIntegrityViolationException exception) {
                    throw new PhoneNumberUniqueConstraintException(countryDTO);
                }
            }

            if (countryDTO.getCityList() != null) {
                for (CityDTO cityDTO : countryDTO.getCityList()) {
                    cityService.createCity(cityDTO);
                }

                countryFoundedById.get().getCityList().clear();
                countryFoundedById.get().getCityList().addAll(CityAdapter.fromListDTO(countryDTO.getCityList()));

                try {
                    countryRepository.save(countryFoundedById.get());
                } catch (DataIntegrityViolationException exception) {
                    for (CityDTO cityDTO : countryDTO.getCityList()) {
                        if (!cityService.isNameUnique(cityDTO.getName())) {
                            throw new NameUniqueConstraintException(cityDTO);
                        }
                    }
                }
            }
        } else {
            throw new CountryNotFoundException(countryDTO.getId());
        }

        return CountryAdapter.toDTO(countryFoundedById.get());
    }

    @Validated(OnCreate.class)
    public CountryDTO patchCountryAddCities(Integer id, List<CityDTO> cityDTOList) {

        Optional<Country> countryFoundedById = countryRepository.findById(id);

        if (countryFoundedById.isPresent()) {
            for (CityDTO cityDTO : cityDTOList) {
                cityService.createCity(cityDTO);
            }
            countryFoundedById.get().getCityList().addAll(CityAdapter.fromListDTO(cityDTOList));

            try {
                countryRepository.save(countryFoundedById.get());
            } catch (DataIntegrityViolationException exception) {
                for (CityDTO cityDTO : cityDTOList) {
                    if (!cityService.isNameUnique(cityDTO.getName())) {
                        throw new NameUniqueConstraintException(cityDTO);
                    }
                }
            }

            return CountryAdapter.toDTO(countryFoundedById.get());
        } else {
            throw new CountryNotFoundException(id);
        }
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



    private boolean existsID(Integer id) {
        Optional<Country> countryFoundedById = countryRepository.findById(id);

        return countryFoundedById.isPresent();
    }

    private boolean isUniqueCountryName(String name) {
        Optional<Country> countryFounded = countryRepository.findByName(name);

        return !countryFounded.isPresent();
    }

    private boolean isUniquePhoneNumber(String phoneNumber) {
        Optional<Country> countryFounded = countryRepository.findByPhoneNumber(phoneNumber);

        return !countryFounded.isPresent();
    }



    private void nameToUpper(CountryDTO countryDTO) {
        countryDTO.setName(countryDTO.getName().toUpperCase());
    }
}
