package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.DealershipDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.model.adapters.DealershipAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.repository.dealership.Dealership;
import com.rentacar.repository.dealership.DealershipRepository;
import com.rentacar.service.exceptions.dealership.DealershipAlreadyExistsException;
import com.rentacar.service.exceptions.dealership.DealershipNotFoundException;
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
            throw new DealershipAlreadyExistsException(DealershipAdapter.toDTO(dealershipFounded.get()));
        }
    }

    public DealershipDTO addACarsToDealership(Integer id, List<CarDTO> carDTOList) {

        Optional<Dealership> dealershipFounded = dealershipRepository.findById(id);

        if (dealershipFounded.isPresent()) {

            for (CarDTO carDTO: carDTOList) {
                carService.createCar(carDTO);
            }

            dealershipFounded.get().getCars().addAll(CarAdapter.fromListDTO(carDTOList));

            return DealershipAdapter.toDTO(dealershipRepository.save(dealershipFounded.get()));

        } else {
            throw new DealershipNotFoundException(id);
        }
    }
}
