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

        if (!existsName(countryDTO.getName()) && !existsPhoneNumber(countryDTO.getPhoneNumber())) {
            for (CityDTO cityDTO : countryDTO.getCityList()) {
                cityService.createCity(cityDTO);
            }

            return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));

        } else if (existsName(countryDTO.getName()) && existsPhoneNumber(countryDTO.getPhoneNumber())) {
            throw new CountryAlreadyExistsException(countryDTO, "name", "phone number");
        } else if (existsName(countryDTO.getName())) {
            throw new CountryAlreadyExistsException(countryDTO, "name");
        } else {
            throw new CountryAlreadyExistsException(countryDTO, "phone number");
        }
    }

    @Validated(OnUpdate.class)
    public CountryDTO updateCountry(@Valid CountryDTO countryDTO) {
        nameToUpper(countryDTO);

        if (existsID(countryDTO.getId()) && !existsName(countryDTO.getName())) {
            for (CityDTO cityDTO : countryDTO.getCityList()) {
                cityService.createCity(cityDTO);
            }

            return CountryAdapter.toDTO(countryRepository.save(CountryAdapter.fromDTO(countryDTO)));

        } else if (!existsID(countryDTO.getId())) {
            throw new CountryNotFoundException(countryDTO.getId());
        } else {
            throw new CountryAlreadyExistsException(countryDTO);
        }
    }

    @Validated(OnUpdate.class)
    public CountryDTO patchCountry(CountryDTO countryDTO) {

        Optional<Country> countryFoundedById = countryRepository.findById(countryDTO.getId());

        if (countryFoundedById.isPresent()) {
            if (countryDTO.getName() != null) {
                nameToUpper(countryDTO);

                if (!existsName(countryDTO.getName())) {
                    countryFoundedById.get().setName(countryDTO.getName());

                    return CountryAdapter.toDTO(countryRepository.save(countryFoundedById.get()));
                } else {
                    throw new CountryAlreadyExistsException(countryDTO, "name");
                }
            }

            if (countryDTO.getPhoneNumber() != null) {
                if (!existsPhoneNumber(countryDTO.getPhoneNumber())) {
                    countryFoundedById.get().setPhoneNumber(countryDTO.getPhoneNumber());

                    return CountryAdapter.toDTO(countryRepository.save(countryFoundedById.get()));
                } else {
                    throw new CountryAlreadyExistsException(countryDTO, "phone number");
                }
            }

            if (countryDTO.getCityList() != null) {
                for (CityDTO cityDTO : countryDTO.getCityList()) {
                    cityService.createCity(cityDTO);
                }

                countryFoundedById.get().setCityList(CityAdapter.fromListDTO(countryDTO.getCityList()));
                return CountryAdapter.toDTO(countryRepository.save(countryFoundedById.get()));
            }
        } else {
            throw new CountryNotFoundException(countryDTO.getId());
        }

        // Totul trimis a fost null
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
            countryRepository.save(countryFoundedById.get());

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

    private boolean existsPhoneNumber(String phoneNumber) {
        Optional<Country> countryFoundedByPhoneNumber = countryRepository.findByPhoneNumber(phoneNumber);

        return countryFoundedByPhoneNumber.isPresent();
    }

    private boolean existsName(String name) {
        Optional<Country> countryFoundedByName = countryRepository.findByName(name);

        return countryFoundedByName.isPresent();
    }



    private void nameToUpper(CountryDTO countryDTO) {
        countryDTO.setName(countryDTO.getName().toUpperCase());
    }
}
