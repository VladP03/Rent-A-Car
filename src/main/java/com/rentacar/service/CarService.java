package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.car.*;
import com.rentacar.service.validations.Car.CarValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

@Service
@Validated
public class CarService {

    private final CarRepository carRepository;



    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }



    public List<CarDTO> getListOfCars(Integer id, String brandName) {
        if (id == null && brandName == null) {
            return CarAdapter.toListDTO(carRepository.findAll());
        }

        if (id != null && brandName != null) {
            // all car's brand name in database are in upper case
            brandName = brandName.toUpperCase();
            return Collections.singletonList(getCar(id, brandName));
        }

        if (id == null) {
            // all car's brand name in database are in upper case
            brandName = brandName.toUpperCase();
            return getCar(brandName);
        }

        return Collections.singletonList(getCar(id));
    }


    @Validated(OnCreate.class)
    public void createCar(@Valid CarDTO carDTO) {
        new CarValidation(carDTO, carRepository).validateCreate();
    }


    @Validated(OnUpdate.class)
    public void updateCar(@Valid CarDTO carDTO) {
        new CarValidation(carDTO, carRepository).validateUpdate();
    }


    @Validated(OnUpdate.class)
    public void patchCar(CarDTO carDTO) {
        new CarValidation(carDTO, carRepository).validatePatch();
    }



    // Admin

    @Validated(OnCreate.class)
    public CarDTO createCarAdmin(@Valid CarDTO carDTO) {
        createCar(carDTO);
        return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(carDTO)));
    }


    @Validated(OnUpdate.class)
    public CarDTO updateCarAdmin(@Valid CarDTO carDTO) {
        updateCar(carDTO);
        return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(carDTO)));
    }


    @Validated(OnUpdate.class)
    public CarDTO patchCarAdmin(CarDTO carDTO) {
        patchCar(carDTO);
        return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(carDTO)));
    }


    @Validated(OnUpdate.class)
    public CarDTO deleteCarAdmin(CarDTO carDTO) {
        new CarValidation(carDTO, carRepository).validateDelete();
        carRepository.delete(CarAdapter.fromDTO(carDTO));
        return carDTO;
    }



    // Small functions

    private CarDTO getCar(Integer id, String brandName) {
        Optional<Car> carFounded = carRepository.findByIDAndBrandName(id, brandName);

        if (carFounded.isPresent()) {
            return CarAdapter.toDTO(carFounded.get());
        } else {
            throw new CarNotFoundException(id, brandName);
        }
    }


    private CarDTO getCar(Integer id) {
        Optional<Car> carFounded = carRepository.findById(id);

        if (carFounded.isPresent()) {
            return CarAdapter.toDTO(carFounded.get());
        } else {
            throw new CarNotFoundException(id);
        }
    }


    private List<CarDTO> getCar(String brandName) {
        Optional<List<Car>> carFounded = carRepository.findByBrandName(brandName);

        if (carFounded.isPresent()) {
            return CarAdapter.toListDTO(carFounded.get());
        } else {
            throw new CarNotFoundException(brandName);
        }
    }
}
