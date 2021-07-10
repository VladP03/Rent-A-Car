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

    public List<CarDTO> getCar(Integer id, String brandName) {
        if (id == null && brandName == null) {
            return CarAdapter.toListDTO(carRepository.findAll());
        } else {
            Optional<List<Car>> carFounded = carRepository.findByIDOrBrandName(id, brandName);

            if (carFounded.isPresent()) {
                return CarAdapter.toListDTO(carFounded.get());
            } else if (brandName == null){
                throw new CarNotFoundException(id);
            } else {
                throw new CarNotFoundException(brandName);
            }
        }
    }

    @Validated(OnCreate.class)
    public CarDTO createCarAdmin(@Valid CarDTO carDTO) {
        namesToUpper(carDTO);

        checkIfCarAlreadyExists(carDTO);
        checkFirstRegistration(carDTO);
        checkFuel(carDTO);
        checkGearbox(carDTO);

        return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(carDTO)));
    }

    @Validated(OnCreate.class)
    public void createCar(@Valid CarDTO carDTO) {
        namesToUpper(carDTO);

        checkIfCarAlreadyExists(carDTO);
        checkFirstRegistration(carDTO);
        checkFuel(carDTO);
        checkGearbox(carDTO);

    }

    @Validated(OnUpdate.class)
    public CarDTO updateCar(@Valid CarDTO carDTO) {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (carFounded.isPresent()) {
            namesToUpper(carDTO);

            checkFirstRegistration(carDTO);
            checkFuel(carDTO);
            checkGearbox(carDTO);

            carRepository.save(CarAdapter.fromDTO(carDTO));

            return carDTO;
        } else {
            throw new CarNotFoundException(carDTO.getID());
        }
    }

    @Validated(OnUpdate.class)
    public CarDTO patchCar(CarDTO carDTO) {
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
            throw new CarNotFoundException(carDTO.getID());
        }
    }

    public CarDTO deleteCar(Integer id) {
        Optional<Car> carFounded = carRepository.findById(id);

        if (carFounded.isPresent()) {
            carRepository.deleteById(id);

            return CarAdapter.toDTO(carFounded.get());
        } else {
            throw new CarNotFoundException(id);
        }
    }



    private void checkIfCarAlreadyExists(CarDTO carDTO) {
        if (carRepository.findByVIN(carDTO.getVIN()).isPresent()) {
            throw new CarAlreadyExistsException(carDTO);
        }
    }

    private void checkFirstRegistration(CarDTO carDTO) {
        if (carDTO.getFirstRegistration() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new CarFirstRegistrationException(carDTO);
        } else if (carDTO.getFirstRegistration() < Calendar.getInstance().get(Calendar.YEAR) - 10) {
            throw new CarFirstRegistrationException(carDTO);
        }
    }

    private void checkFuel(CarDTO carDTO) {
        List<String> fuelType = new ArrayList<>();

        fuelType.add("GAS");
        fuelType.add("DIESEL");
        fuelType.add("HYBRID");
        fuelType.add("ELECTRIC");

        if (!fuelType.contains(carDTO.getFuel().toUpperCase())) {
            throw new CarFuelException(carDTO);
        }
    }

    private void checkGearbox(CarDTO carDTO) {
        List<String> gearBoxType = new ArrayList<>();

        gearBoxType.add("MANUAL");
        gearBoxType.add("AUTOMATIC");

        if (!gearBoxType.contains(carDTO.getGearbox().toUpperCase())) {
            throw new CarGearboxException(carDTO);
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
