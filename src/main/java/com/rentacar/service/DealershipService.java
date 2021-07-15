package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.DealershipDTO;
import com.rentacar.model.adapters.DealershipAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.repository.dealership.Dealership;
import com.rentacar.repository.dealership.DealershipRepository;
import com.rentacar.service.exceptions.dataIntegrity.EmailConstraintException;
import com.rentacar.service.exceptions.dealership.DealershipAlreadyExistsException;
import com.rentacar.service.exceptions.dealership.DealershipNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Service
@Validated
public class DealershipService {

    private final DealershipRepository dealershipRepository;
    private final CarService carService;
    private final CountryService countryService;

    public DealershipService(DealershipRepository dealershipRepository, CarService carService, CountryService countryService) {
        this.dealershipRepository = dealershipRepository;
        this.carService = carService;
        this.countryService = countryService;
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
    public DealershipDTO createDealership(@Valid DealershipDTO dealershipDTO) {

        Optional<Dealership> dealershipFounded = dealershipRepository.findByName(dealershipDTO.getName());

        if (!dealershipFounded.isPresent()) {
            namesToUpper(dealershipDTO);

            // cars valid
            for (CarDTO carDTO : dealershipDTO.getCars()) {
                carService.createCar(carDTO);
            }

            if (!countryService.existsCountry(dealershipDTO.getCountry())) {
                throw new RuntimeException();
            }

            if (!countryService.existsCityInCountry(dealershipDTO.getCity())) {
                throw new RuntimeException();
            }

            try {
                return DealershipAdapter.toDTO(dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO)));
            } catch (DataIntegrityViolationException exception) {
                if (!isUniqueEmail(dealershipDTO.getEmail())) {
                    throw new EmailConstraintException(dealershipDTO.getClass(), dealershipDTO.getEmail());
                }
            }
        } else {
            throw new DealershipAlreadyExistsException(DealershipAdapter.toDTO(dealershipFounded.get()));
        }

        return null;
    }


    private boolean isUniqueEmail(String email) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findByEmail(email);

        return !dealershipFounded.isPresent();
    }



    private void namesToUpper(DealershipDTO dealershipDTO) {
        dealershipDTO.setName(dealershipDTO.getName().toUpperCase());
        dealershipDTO.setEmail(dealershipDTO.getEmail().toUpperCase());
    }
}
