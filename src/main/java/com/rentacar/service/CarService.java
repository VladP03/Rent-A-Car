package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.car.*;
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

    public List<CarDTO> getAll() {
        return CarAdapter.toListDTO(carRepository.findAll());
    }

    public CarDTO getCar(Integer id) {
        Optional<Car> carFounded = carRepository.findById(id);

        if (carFounded.isPresent()) {
            return CarAdapter.toDTO(carFounded.get());
        } else {
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

    @Validated(OnUpdate.class)
    public CarDTO updateCar(@Valid CarDTO carDTO) {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (carFounded.isPresent()) {
            namesToUpper(carDTO);

            checkFirstRegistration(carDTO);
            checkFuel(carDTO);
            checkGearbox(carDTO);

            carRepository.deleteById(carDTO.getID());
            carRepository.save(CarAdapter.fromDTO(carDTO));

            return carDTO;
        } else {
            throw new CarNotFoundException("The car with ID " + carDTO.getID() + " doesn't exists in database.");
        }
    }

    @Validated(OnUpdate.class)
    public CarDTO pathcCar(CarDTO carDTO) {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (carFounded.isPresent()) {

            if (carDTO.getBrandName() != null) {
                carFounded.get().setBrandName(carDTO.getBrandName());
            }

            if (carDTO.getName() != null) {
                carFounded.get().setName(carDTO.getName());
            }

            if (carDTO.getVIN() != null) {
                carFounded.get().setVIN(carDTO.getVIN());
            }

            if (carDTO.getFirstRegistration() != null) {
                checkFirstRegistration(carDTO);
                carFounded.get().setFirstRegistration(carDTO.getFirstRegistration());
            }

            if (carDTO.getEngineCapacity() != null) {
                carFounded.get().setEngineCapacity(carDTO.getEngineCapacity());
            }

            if (carDTO.getFuel() != null) {
                checkFuel(carDTO);
                carFounded.get().setFuel(carDTO.getFuel());
            }

            if (carDTO.getGearbox() != null) {
                checkGearbox(carDTO);
                carFounded.get().setGearbox(carDTO.getGearbox());
            }

            CarDTO finalCar = CarAdapter.toDTO(carFounded.get());
            namesToUpper(finalCar);
            carRepository.save(CarAdapter.fromDTO(finalCar));

            return finalCar;
        } else {
            throw new CarNotFoundException("The car with ID " + carDTO.getID() + " doesn't exists in database.");
        }
    }

    public CarDTO deleteCar(Integer id) {
        Optional<Car> carFounded = carRepository.findById(id);

        if (carFounded.isPresent()) {
            carRepository.deleteById(id);

            return CarAdapter.toDTO(carFounded.get());
        } else {
            throw new CarNotFoundException("The car with ID " + id + " doesn't exists in database.");
        }
    }



    private void checkIfCarAlreadyExists(CarDTO carDTO) {
        if (carRepository.findByVIN(carDTO.getVIN()) != null) {
            throw new CarAlreadyExistsException("Car already exists in db.");
        }
    }

    private void checkFirstRegistration(CarDTO carDTO) {
        if (carDTO.getFirstRegistration() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new CarFirstRegistrationException("Car firstRegistration can not be greater than current year");
        } else if (carDTO.getFirstRegistration() < Calendar.getInstance().get(Calendar.YEAR) - 10) {
            throw new CarFirstRegistrationException("Car firstRegistration can not be older than 10 years");
        }
    }

    private void checkFuel(CarDTO carDTO) {
        List<String> fuelType = new ArrayList<>();

        fuelType.add("GAS");
        fuelType.add("DIESEL");
        fuelType.add("HYBRID");
        fuelType.add("ELECTRIC");

        if (!fuelType.contains(carDTO.getFuel().toUpperCase())) {
            throw new CarFuelException("Car fuel type is incorrect!");
        }
    }

    private void checkGearbox(CarDTO carDTO) {
        List<String> gearBoxType = new ArrayList<>();

        gearBoxType.add("MANUAL");
        gearBoxType.add("AUTOMATIC");

        if (!gearBoxType.contains(carDTO.getGearbox().toUpperCase())) {
            throw new CarGearboxException("Car gearbox is incorrect!");
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
