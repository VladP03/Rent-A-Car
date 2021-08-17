package com.rentacar.service;

import com.rentacar.model.CityDTO;
import com.rentacar.model.CountryDTO;
import com.rentacar.model.adapters.CountryAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.country.Country;
import com.rentacar.repository.country.CountryRepository;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.NameUniqueConstraintException;
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



    public List<CountryDTO> getListOfCars(Integer id, String name) {
        if (id == null && name == null) {
            return CountryAdapter.toListDTO(countryRepository.findAll());
        }

        if (id != null && name != null) {
            // all countries name in database are in upper case
            name = name.toUpperCase();
            return Collections.singletonList(getCountry(id, name));
        }

        if (id == null) {
            // all countries name in database are in upper case
            name = name.toUpperCase();
            return Collections.singletonList(getCountry(name));
        }

        return Collections.singletonList(getCountry(id));
    }

    @Validated(OnCreate.class)
    public CountryDTO createCountry(@Valid CountryDTO countryDTO) {
        stringVariablesToUpper(countryDTO);

        checkCitiesListIfItsNull(countryDTO.getCityList());
        createCities(countryDTO.getCityList());
        checkNameIsUnique(countryDTO);
        checkPhoneNumberIsUnique(countryDTO);

        return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));
    }

    @Validated(OnUpdate.class)
    public CountryDTO updateCountry(@Valid CountryDTO countryDTO) {
        getCountry(countryDTO.getId());

        stringVariablesToUpper(countryDTO);

        checkCitiesListIfItsNull(countryDTO.getCityList());
        createCities(countryDTO.getCityList());
        checkNameIsUnique(countryDTO);
        checkPhoneNumberIsUnique(countryDTO);

        return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));
    }

    @Validated(OnUpdate.class)
    public CountryDTO patchCountry(@Valid CountryDTO countryDTO) {
        CountryDTO countryToPatch = getCountry(countryDTO.getId());

        stringVariablesToUpper(countryDTO);

        patchName(countryToPatch, countryDTO);
        patchPhoneNumber(countryToPatch, countryDTO);
        patchCitiesLies(countryToPatch, countryDTO);

        return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryToPatch)));
    }

    @Validated(OnCreate.class)
    public CountryDTO patchCountryAddCities(Integer id, @Valid List<CityDTO> cityDTOList) {
        CountryDTO countryToPatch = getCountry(id);

        checkCitiesListIfItsNull(cityDTOList);
        createCities(cityDTOList);

        return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryToPatch)));
    }

    public CountryDTO deleteCountry(Integer id) {
        CountryDTO countryToDelete = getCountry(id);
        countryRepository.deleteById(id);

        return countryToDelete;
    }



    // Small functions

    private CountryDTO getCountry(Integer id, String name) {
        Optional<Country> countryFounded = countryRepository.findByIdAndName(id, name);

        if (countryFounded.isPresent()) {
            return CountryAdapter.toDTO(countryFounded.get());
        } else {
            throw new CountryNotFoundException(id, name);
        }
    }


    private CountryDTO getCountry(Integer id) {
        Optional<Country> countryFounded = countryRepository.findById(id);

        if (countryFounded.isPresent()) {
            return CountryAdapter.toDTO(countryFounded.get());
        } else {
            throw new CountryNotFoundException(id);
        }
    }


    private CountryDTO getCountry(String name) {
        Optional<Country> countryFounded = countryRepository.findByName(name);

        if (countryFounded.isPresent()) {
            return CountryAdapter.toDTO(countryFounded.get());
        } else {
            throw new CountryNotFoundException(name);
        }
    }


    private void createCities(List<CityDTO> cityDTOList) {
        for (CityDTO cityDTO : cityDTOList) {
            cityService.createCity(cityDTO);
        }
    }

    private void checkCitiesListIfItsNull(List<CityDTO> cityDTOList) {
        if (cityDTOList == null) {
            cityDTOList = Collections.emptyList();
        }
    }



    private void checkNameIsUnique(CountryDTO countryDTO) {
        Optional<Country> countryFounded = countryRepository.findByName(countryDTO.getName());

        if (countryFounded.isPresent()) {
            throw new NameUniqueConstraintException(countryDTO);
        }
    }


    private void checkPhoneNumberIsUnique(CountryDTO countryDTO) {
        Optional<Country> countryFounded = countryRepository.findByPhoneNumber(countryDTO.getPhoneNumber());

        if (countryFounded.isPresent()) {
            throw new NameUniqueConstraintException(countryDTO);
        }
    }


    private void patchName(CountryDTO destination, CountryDTO source) {
        if (source.getName() != null) {
            checkNameIsUnique(source);
            destination.setName(source.getName());
        }
    }

    private void patchPhoneNumber(CountryDTO destination, CountryDTO source) {
        if (source.getName() != null) {
            checkPhoneNumberIsUnique(source);
            destination.setPhoneNumber(source.getName());
        }
    }

    private void patchCitiesLies(CountryDTO destination, CountryDTO source) {
        if (source.getCityList() != null) {
            createCities(source.getCityList());

            destination.getCityList().clear();
            destination.getCityList().addAll(source.getCityList());
        }
    }



    // Make all Country's String variables to Upper for a better look in database
    private void stringVariablesToUpper(CountryDTO countryDTO) {
        countryDTO.setName(countryDTO.getName().toUpperCase());
    }
}
