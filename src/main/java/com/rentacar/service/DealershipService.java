package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.CityDTO;
import com.rentacar.model.DealershipDTO;
import com.rentacar.model.adapters.DealershipAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.repository.dealership.Dealership;
import com.rentacar.repository.dealership.DealershipRepository;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.EmailUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.PhoneNumberUniqueConstraintException;
import com.rentacar.service.exceptions.dealership.DealershipCityInvalidException;
import com.rentacar.service.exceptions.dealership.DealershipNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class DealershipService {

    private final DealershipRepository dealershipRepository;
    private final CarService carService;
    private final CountryService countryService;
    private final CityService cityService;

    public DealershipService(DealershipRepository dealershipRepository, CarService carService, CountryService countryService, CityService cityService) {
        this.dealershipRepository = dealershipRepository;
        this.carService = carService;
        this.countryService = countryService;
        this.cityService = cityService;
    }

    public List<DealershipDTO> getDealership(Integer id, String name) {
        if (id == null && name == null) {
            return DealershipAdapter.toListDTO(dealershipRepository.findAll());
        } else if (id != null && name != null) {
            Optional<Dealership> dealershipFounded = dealershipRepository.findByIDAndName(id, name);

            if (dealershipFounded.isPresent()) {
                return Collections.singletonList(DealershipAdapter.toDTO(dealershipFounded.get()));
            } else {
                throw  new DealershipNotFoundException(id, name);
            }
        } else {
            Optional<Dealership> dealershipFounded = dealershipRepository.findByIDOrName(id, name);

            if (dealershipFounded.isPresent()) {
                return Collections.singletonList(DealershipAdapter.toDTO(dealershipFounded.get()));
            } else if (name == null){
                throw new DealershipNotFoundException(id);
            } else {
                throw new DealershipNotFoundException(name);
            }
        }
    }

    @Validated(OnCreate.class)
    public DealershipDTO createDealership(@Valid DealershipDTO dealershipDTO) throws DataIntegrityViolationException {
        if (countryService.getCountry(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName()) != null) {
            if (cityService.getCity(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName()) != null) {
                namesToUpper(dealershipDTO);

                if (!isCityInCountryCitiesList(dealershipDTO)) {
                    throw new DealershipCityInvalidException(dealershipDTO);
                }

                for (CarDTO carDTO : dealershipDTO.getCars()) {
                    carService.createCar(carDTO);
                }

                rewritePhoneNumber(dealershipDTO);

                try {
                    return DealershipAdapter.toDTO(dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO)));
                } catch (DataIntegrityViolationException exception) {
                    if (!isUniqueEmail(dealershipDTO.getEmail())) {
                        throw new EmailUniqueConstraintException(dealershipDTO);
                    }

                    if (!isUniquePhoneNumber(dealershipDTO.getPhoneNumber())) {
                        throw new PhoneNumberUniqueConstraintException(dealershipDTO);
                    }

                    throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
                }
            } else {
                throw new CityNotFoundException(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName());
            }
        } else {
            throw new CountryNotFoundException(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName());
        }
    }



    private boolean isUniqueEmail(String email) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findByEmail(email);

        return !dealershipFounded.isPresent();
    }

    private boolean isUniquePhoneNumber(String phoneNumber) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findByPhoneNumber(phoneNumber);

        return !dealershipFounded.isPresent();
    }

    private boolean isCityInCountryCitiesList(DealershipDTO dealershipDTO) {

        for (CityDTO cityDTO : dealershipDTO.getCountry().getCityList()) {
            if (cityDTO.equals(dealershipDTO.getCity())) {
                return true;
            }
        }

        return false;
    }

    private void rewritePhoneNumber (DealershipDTO dealershipDTO) {
        String dealerNumber = dealershipDTO.getPhoneNumber();
        String countryNumber = dealershipDTO.getCountry().getPhoneNumber();

        String dealerNumberVisible = dealerNumber.substring(0,3) + "-" + dealerNumber.substring(3, 7) + "-" + dealerNumber.substring(7);

        dealershipDTO.setPhoneNumber(countryNumber + " " + dealerNumberVisible);
    }



    private void namesToUpper(DealershipDTO dealershipDTO) {
        dealershipDTO.setName(dealershipDTO.getName().toUpperCase());
        dealershipDTO.setEmail(dealershipDTO.getEmail().toUpperCase());
        dealershipDTO.getCity().setName(dealershipDTO.getCity().getName().toUpperCase());
    }
}
