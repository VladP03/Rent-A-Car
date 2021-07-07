package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
@Validated
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

    @Validated(OnCreate.class)
    public CarDTO createCar(@Valid CarDTO carDTO) {
        namesToUpper(carDTO);

        checkIfCarAlreadyExists(carDTO);
        checkFirstRegistration(carDTO);
        checkFuel(carDTO);
        checkGearbox(carDTO);

        return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(carDTO)));
    }



    private void checkIfCarAlreadyExists(CarDTO carDTO) {
        if (carRepository.findByVIN(carDTO.getVIN()) != null) {
            throw new CarAlreadyExistsException("Car already exists in db.");
        }
    }

    private void checkFirstRegistration(CarDTO carDTO) {
        if (carDTO.getFirstRegistration() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new CarFirstRegistrationException("Car firstRegistration given is: " + carDTO.getFirstRegistration());
        } else if (carDTO.getFirstRegistration() < Calendar.getInstance().get(Calendar.YEAR) - 10) {
            throw new CarFirstRegistrationException("Car firstRegistration given is: " + carDTO.getFirstRegistration());
        }
    }

    private void checkFuel(CarDTO carDTO) {
        List<String> fuelType = new ArrayList<>();

        fuelType.add("GAS");
        fuelType.add("DIESEL");
        fuelType.add("HYBRID");
        fuelType.add("ELECTRIC");

        if (!fuelType.contains(carDTO.getFuel())) {
            throw new CarFuelException("Car fuel type is incorrect!");
        }
    }

    private void checkGearbox(CarDTO carDTO) {
        if (!carDTO.getGearbox().equals("MANUAL") && !carDTO.getGearbox().equals("AUTOMATIC")) {
            throw new CarGearboxException("Car gearbox is incorrect");
        }
    }

    private void namesToUpper(CarDTO carDTO) {
        carDTO.setBrandName(carDTO.getBrandName().toUpperCase());
        carDTO.setName(carDTO.getName().toUpperCase());
        carDTO.setVIN(carDTO.getVIN().toUpperCase());
        carDTO.setFuel(carDTO.getFuel().toUpperCase());
        carDTO.setGearbox(carDTO.getGearbox().toUpperCase());
    }
}
