package com.rentacar.service.validations.Car;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.car.CarNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.VinUniqueConstraintException;
import com.rentacar.service.validations.Car.BusinessLogic.CarBusinessLogic;
import com.rentacar.service.validations.Car.UniqueConstraint.VINUniqueConstraint;

import java.util.Optional;

public class CarValidation {

    private final CarDTO carDTO;
    private final CarRepository carRepository;
    private final CarBusinessLogic carBusinessLogic = new CarBusinessLogic();



    public CarValidation(CarDTO carDTO, CarRepository carRepository) {
        this.carRepository = carRepository;
        this.carDTO = carDTO;

        stringVariablesToUpper();
    }



    public void validateCreate() {
        validateVIN();
        carBusinessLogic.validateBusinessLogic(carDTO);
    }


    public void validateUpdate() {
        checkIfIDExists();

        if (haveANewVIN()) {
            validateVIN();
        }

        carBusinessLogic.validateBusinessLogic(carDTO);
    }


    public void validatePatch() {
        CarDTO car = getCar();
        patchCar(car);

        carBusinessLogic.validateBusinessLogic(carDTO);
    }


    public void validateDelete() {
        checkIfIDExists();
    }



    // Small functions

    private void checkIfIDExists() {
        boolean isPresent = carRepository.findByID(carDTO.getID());

        if (!isPresent) {
            throw new CarNotFoundException(carDTO.getID());
        }
    }


    private boolean haveANewVIN() {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        return !carFounded.get().getVIN().equals(carDTO.getVIN());
    }


    private void validateVIN() {
        try {
            VINUniqueConstraint.checkConstraint(carDTO.getVIN(), carRepository);
        } catch (VinUniqueConstraintException exception) {
            throw new VinUniqueConstraintException(carDTO);
        }
    }



    // Get functions

    private CarDTO getCar() {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (carFounded.isPresent()) {
            return CarAdapter.toDTO(carFounded.get());
        } else {
            throw new CarNotFoundException(carDTO.getID());
        }
    }



    // Patch functions

    private void patchCar(CarDTO car) {
        patchBrandName(car.getBrandName());
        patchName(car.getName());
        patchVIN(car.getVIN());
        patchFirstRegistration(car.getFirstRegistration());
        patchEngineCapacity(car.getEngineCapacity());
        patchFuel(car.getFuel());
        patchGearBox(car.getGearbox());
        patchMileage(car.getMileage());
    }


    private void patchBrandName(String newBrandName) {
        if (carDTO.getBrandName() == null) {
            carDTO.setBrandName(newBrandName);
        }
    }


    private void patchName(String newName) {
        if (carDTO.getName() == null) {
            carDTO.setName(newName);
        }
    }


    private void patchVIN(String newVIN) {
        if (carDTO.getVIN() == null) {
            carDTO.setVIN(newVIN);
        } else if (haveANewVIN()) {
            validateVIN();
        }
    }


    private void patchFirstRegistration(Integer newFirstRegistration) {
        if (carDTO.getFirstRegistration() == null) {
            carDTO.setFirstRegistration(newFirstRegistration);
        }
    }


    private void patchEngineCapacity(Integer newEngineCapacity) {
        if (carDTO.getEngineCapacity() == null) {
            carDTO.setEngineCapacity(newEngineCapacity);
        }
    }


    private void patchFuel(String newFuel) {
        if (carDTO.getFuel() == null) {
            carDTO.setFuel(newFuel);
        }
    }


    private void patchGearBox(String newGearBox) {
        if (carDTO.getGearbox() == null) {
            carDTO.setGearbox(newGearBox);
        }
    }


    private void patchMileage(Double newMileage) {
        if (carDTO.getMileage() == null) {
            carDTO.setMileage(newMileage);
        }
    }



    // Make all Car's String variables to Upper for a better look in database
    private void stringVariablesToUpper() {
        if (carDTO.getBrandName() != null) {
            carDTO.setBrandName(carDTO.getBrandName().toUpperCase());
        }

        if (carDTO.getName() != null) {
            carDTO.setName(carDTO.getName().toUpperCase());
        }

        if (carDTO.getVIN() != null) {
            carDTO.setVIN(carDTO.getVIN().toUpperCase());
        }

        if (carDTO.getFuel() != null) {
            carDTO.setFuel(carDTO.getFuel().toUpperCase());
        }

        if (carDTO.getGearbox() != null) {
            carDTO.setGearbox(carDTO.getGearbox().toUpperCase());
        }
    }
}
