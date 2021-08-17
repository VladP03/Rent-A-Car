package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.exceptions.car.*;
import com.rentacar.service.exceptions.dataIntegrity.VinUniqueConstraintException;
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
        stringVariablesToUpper(carDTO);

        checkIfVINExists(carDTO);
        checkFirstRegistration(carDTO);
        checkFuel(carDTO);
        checkGearbox(carDTO);
    }

    @Validated(OnUpdate.class)
    public void updateCar(@Valid CarDTO carDTO) {
        checkIfCarExists(carDTO);

        checkIfVINExists(carDTO);
        checkFirstRegistration(carDTO);
        checkFuel(carDTO);
        checkGearbox(carDTO);
    }

    @Validated(OnUpdate.class)
    public CarDTO patchCar(CarDTO carDTO) {
        CarDTO carToPatch = getCar(carDTO.getID());

        patchBrandName(carToPatch, carDTO);
        patchName(carToPatch, carDTO);
        patchVIN(carToPatch, carDTO);
        patchFirstRegistration(carToPatch, carDTO);
        patchEngineCapacity(carToPatch, carDTO);
        patchFuel(carToPatch, carDTO);
        patchGearBox(carToPatch, carDTO);

        stringVariablesToUpper(carToPatch);

        return carToPatch;
    }

    public CarDTO deleteCar(Integer id) {
        return getCar(id);
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
        CarDTO carToPatch = patchCar(carDTO);

        return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(carToPatch)));
    }

    public CarDTO deleteCarAdmin(Integer id) {
        CarDTO carToDelete = deleteCar(id);
        carRepository.deleteById(id);

        return carToDelete;
    }



    // Business logic

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


    private void checkIfVINExists(CarDTO carDTO) {
        Optional<Car> carFounded = carRepository.findByVIN(carDTO.getVIN());

        if (carFounded.isPresent()) {
            throw new VinUniqueConstraintException(carDTO);
        }
    }


    private void checkIfCarExists(CarDTO carDTO) {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (!carFounded.isPresent()) {
            throw new CarNotFoundException(carDTO.getID());
        }
    }

    private void patchBrandName(CarDTO destination, CarDTO source) {
        if (source.getBrandName() != null) {
            destination.setBrandName(source.getBrandName());
        }
    }


    private void patchName(CarDTO destination, CarDTO source) {
        if (source.getName() != null) {
            destination.setName(source.getName());
        }
    }


    private void patchVIN(CarDTO destination, CarDTO source) {
        if (source.getVIN() != null) {
            checkIfVINExists(source);
            destination.setVIN(source.getVIN());
        }
    }


    private void patchFirstRegistration(CarDTO destination, CarDTO source) {
        if (source.getFirstRegistration() != null) {
            checkFirstRegistration(source);
            destination.setFirstRegistration(source.getFirstRegistration());
        }
    }


    private void patchEngineCapacity(CarDTO destination, CarDTO source) {
        if (source.getEngineCapacity() != null) {
            source.setEngineCapacity(destination.getEngineCapacity());
        }
    }

    private void patchFuel(CarDTO destination, CarDTO source) {
        if (source.getFuel() != null) {
            checkFuel(source);
            destination.setFuel(destination.getFuel());
        }
    }

    private void patchGearBox(CarDTO destination, CarDTO source) {
        if (source.getGearbox() != null) {
            checkGearbox(source);
            destination.setGearbox(source.getGearbox());
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
