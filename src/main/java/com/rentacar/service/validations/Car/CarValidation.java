package com.rentacar.service.validations.Car;

import com.rentacar.model.CarDTO;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.car.CarNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.VinUniqueConstraintException;
import com.rentacar.service.validations.Car.BusinessLogic.CarBusinessLogic;
import com.rentacar.service.validations.Car.UniqueConstraint.VINUniqueConstraint;

public class CarValidation {

    private final CarDTO carDTO;
    private final CarRepository carRepository;



    public CarValidation(CarDTO carDTO, CarRepository carRepository) {
        this.carRepository = carRepository;
        this.carDTO = carDTO;

        stringVariablesToUpper();
    }



    public void validateCreate() {
        validateVIN();
        checkBusinessLogic();
    }


    public void validateUpdate() {
        checkIfIDExists();
        checkUpdateVIN();
        checkBusinessLogic();
    }


    public void validatePatch(CarDTO carForPatch) {
        patchCar(carForPatch);
        checkBusinessLogic();
    }


    public void validateDelete() {
        checkIfIDExists();
    }



    // Small functions

    private void checkBusinessLogic() {
        CarBusinessLogic.validateBusinessLogic(carDTO);
    }


    private void checkIfIDExists() {
        boolean isPresent = carRepository.findByID(carDTO.getID());

        if (!isPresent) {
            throw new CarNotFoundException(carDTO.getID());
        }
    }


    private void checkUpdateVIN() {
        if (haveANewVIN()) {
            validateVIN();
        }
    }


    private boolean haveANewVIN() {
        return !carRepository.findByVINAndID(carDTO.getVIN(), carDTO.getID());
    }


    private void validateVIN() {
        try {
            VINUniqueConstraint.checkConstraint(carDTO.getVIN(), carRepository);
        } catch (VinUniqueConstraintException exception) {
            throw new VinUniqueConstraintException(carDTO);
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
