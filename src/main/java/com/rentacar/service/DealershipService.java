package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.DealershipDTO;
import com.rentacar.model.adapters.DealershipAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.dealership.Dealership;
import com.rentacar.repository.dealership.DealershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class DealershipService {

    private final DealershipRepository dealershipRepository;
    private final CarService carService;

    public DealershipService(DealershipRepository dealershipRepository, CarService carService) {
        this.dealershipRepository = dealershipRepository;
        this.carService = carService;
    }

    public List<DealershipDTO> getAll() {
        return DealershipAdapter.toListDTO(dealershipRepository.findAll());
    }

    @Validated(OnCreate.class)
    public DealershipDTO createDealership(@Valid DealershipDTO dealershipDTO) {

        Optional<Dealership> dealershipFounded = Optional.ofNullable(dealershipRepository.findByNameAndCountryAndCityAndEmailAndPhoneNumber(dealershipDTO.getName(), dealershipDTO.getCountry(), dealershipDTO.getCity(), dealershipDTO.getEmail(), dealershipDTO.getPhoneNumber()));

        if (!dealershipFounded.isPresent()) {
            for (CarDTO carDTO : dealershipDTO.getCars()) {
                carService.createCar(carDTO);
            }

            return DealershipAdapter.toDTO(dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO)));
        } else {
            throw new RuntimeException("Dealership founded");
        }
    }
}
