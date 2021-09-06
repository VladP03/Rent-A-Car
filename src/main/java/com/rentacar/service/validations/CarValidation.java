package com.rentacar.service.validations;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.car.CarFirstRegistrationException;
import com.rentacar.service.exceptions.car.CarFuelException;
import com.rentacar.service.exceptions.car.CarGearboxException;
import com.rentacar.service.exceptions.car.CarNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.VinUniqueConstraintException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class CarValidation {

    private final CarDTO carDTO;
    private final CarRepository carRepository;


    public CarValidation(CarDTO carDTO, CarRepository carRepository) {
        this.carRepository = carRepository;
        this.carDTO = carDTO;
    }



    public CarDTO validateCreate() {
        checkIfVINExists();
        validateBusinessLogic();

        stringVariablesToUpper(carDTO);

        return carDTO;
    }


    public CarDTO validateUpdate() {
        checkIfCarIDExists();
        checkIfVINExists();
        validateBusinessLogic();

        stringVariablesToUpper(carDTO);

        return carDTO;
    }


    public CarDTO validatePatch() {
        CarDTO car = getCar(carDTO.getID());

        patchBrandName(car.getBrandName());
        patchName(car.getName());
        patchVIN(car.getVIN());
        patchFirstRegistration(car.getFirstRegistration());
        patchEngineCapacity(car.getEngineCapacity());
        patchFuel(car.getFuel());
        patchGearBox(car.getGearbox());

        checkIfVINExists();
        validateBusinessLogic();

        stringVariablesToUpper(carDTO);

        return carDTO;
    }


    public CarDTO validateDelete(Integer id) {
        CarDTO carToDelete = getCar(id);

        carRepository.deleteById(id);

        return carToDelete;
    }



    // Validate business logic

    private void validateBusinessLogic() {
        checkFirstRegistration();
        checkFuel();
        checkGearbox();
    }



    // Business logic

    private void checkFirstRegistration() {
        if (carDTO.getFirstRegistration() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new CarFirstRegistrationException(carDTO);
        } else if (carDTO.getFirstRegistration() < Calendar.getInstance().get(Calendar.YEAR) - 10) {
            throw new CarFirstRegistrationException(carDTO);
        }
    }


    private void checkFuel() {
        List<String> fuelType = new ArrayList<>();

        fuelType.add("GAS");
        fuelType.add("DIESEL");
        fuelType.add("HYBRID");
        fuelType.add("ELECTRIC");

        if (!fuelType.contains(carDTO.getFuel().toUpperCase())) {
            throw new CarFuelException(carDTO);
        }
    }


    private void checkGearbox() {
        List<String> gearBoxType = new ArrayList<>();

        gearBoxType.add("MANUAL");
        gearBoxType.add("AUTOMATIC");

        if (!gearBoxType.contains(carDTO.getGearbox().toUpperCase())) {
            throw new CarGearboxException(carDTO);
        }
    }


    // Small functions

    private void checkIfVINExists() {
        Optional<Car> carFounded = carRepository.findByVIN(carDTO.getVIN());

        if (carFounded.isPresent()) {
            throw new VinUniqueConstraintException(carDTO);
        }
    }


    private void checkIfCarIDExists() {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (!carFounded.isPresent()) {
            throw new CarNotFoundException(carDTO.getID());
        }
    }



    // Get functions

    private CarDTO getCar(Integer id) {
        Optional<Car> carFounded = carRepository.findById(id);

        if (carFounded.isPresent()) {
            return CarAdapter.toDTO(carFounded.get());
        } else {
            throw new CarNotFoundException(id);
        }
    }



    // Patch functions

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



    // Make all Car's String variables to Upper for a better look in database
    private void stringVariablesToUpper(CarDTO carDTO) {
        carDTO.setBrandName(carDTO.getBrandName().toUpperCase());
        carDTO.setName(carDTO.getName().toUpperCase());
        carDTO.setVIN(carDTO.getVIN().toUpperCase());
        carDTO.setFuel(carDTO.getFuel().toUpperCase());
        carDTO.setGearbox(carDTO.getGearbox().toUpperCase());
    }
}
