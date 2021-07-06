package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.CarNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDTO> getAll() {
        return CarAdapter.toListDTO(carRepository.findAll());
    }

    public CarDTO getCar(Integer id) {
        try {
            return CarAdapter.toDTO(carRepository.getById(id));
        } catch (EntityNotFoundException exception) {
            throw new CarNotFoundException("The car with ID " + id + " doesn't exists in database.");
        }
    }

    public List<CarDTO> getCarsWithBrandName(String brandName) {
        return CarAdapter.toListDTO(carRepository.findAllByBrandName(brandName.toUpperCase()));
    }
}
