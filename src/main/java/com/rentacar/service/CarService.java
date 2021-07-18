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
import org.springframework.dao.DataIntegrityViolationException;
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
        } else if (id != null && brandName != null) {

            brandName = brandName.toUpperCase();
            Optional<Car> carFounded = carRepository.findByIDAndBrandName(id, brandName);

            if (carFounded.isPresent()) {
                return Collections.singletonList(CarAdapter.toDTO(carFounded.get()));
            } else {
                throw new CarNotFoundException(id, brandName);
            }
        } else if (brandName != null) {

            brandName = brandName.toUpperCase();
            Optional<List<Car>> carsFounded = carRepository.findByBrandName(brandName);

            if (carsFounded.isPresent()) {
                return CarAdapter.toListDTO(carsFounded.get());
            } else {
                throw new CarNotFoundException(brandName);
            }
        } else {

            Optional<Car> carFounded = carRepository.findById(id);

            if (carFounded.isPresent()) {
                return Collections.singletonList(CarAdapter.toDTO(carFounded.get()));
            } else {
                throw new CarNotFoundException(id);
            }
        }
    }

    @Validated(OnCreate.class)
    public void createCar(@Valid CarDTO carDTO) {
        namesToUpper(carDTO);

        checkFirstRegistration(carDTO);
        checkFuel(carDTO);
        checkGearbox(carDTO);
    }

    @Validated(OnUpdate.class)
    public void updateCar(@Valid CarDTO carDTO) {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (carFounded.isPresent()) {
            namesToUpper(carDTO);

            checkFirstRegistration(carDTO);
            checkFuel(carDTO);
            checkGearbox(carDTO);
        } else {
            throw new CarNotFoundException(carDTO.getID());
        }
    }

    @Validated(OnUpdate.class)
    public void patchCar(CarDTO carDTO) {
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
        } else {
            throw new CarNotFoundException(carDTO.getID());
        }
    }

    public void deleteCar(Integer id) {
        Optional<Car> carFounded = carRepository.findById(id);

        if (!carFounded.isPresent()) {
            throw new CarNotFoundException(id);
        }
    }


    // Admin

    @Validated(OnCreate.class)
    public CarDTO createCarAdmin(@Valid CarDTO carDTO) {
        namesToUpper(carDTO);

        checkFirstRegistration(carDTO);
        checkFuel(carDTO);
        checkGearbox(carDTO);

        try {
            return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(carDTO)));
        } catch (DataIntegrityViolationException exception) {
            if (!isUniqueVIN(carDTO.getVIN())) {
                throw new VinUniqueConstraintException(carDTO);
            }

            throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
        }
    }

    @Validated(OnUpdate.class)
    public CarDTO updateCarAdmin(@Valid CarDTO carDTO) {
        Optional<Car> carFounded = carRepository.findById(carDTO.getID());

        if (carFounded.isPresent()) {
            namesToUpper(carDTO);

            checkFirstRegistration(carDTO);
            checkFuel(carDTO);
            checkGearbox(carDTO);

            try {
                carRepository.save(CarAdapter.fromDTO(carDTO));
            } catch (DataIntegrityViolationException exception) {
                if (!isUniqueVIN(carDTO.getVIN())) {
                    throw new VinUniqueConstraintException(carDTO);
                }

                throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
            }

            return carDTO;
        } else {
            throw new CarNotFoundException(carDTO.getID());
        }
    }

    @Validated(OnUpdate.class)
    public CarDTO patchCarAdmin(CarDTO carDTO) {
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

            try {
                return CarAdapter.toDTO(carRepository.save(CarAdapter.fromDTO(finalCar)));
            } catch (DataIntegrityViolationException exception) {
                if (!isUniqueVIN(carDTO.getVIN())) {
                    throw new VinUniqueConstraintException(carDTO);
                }

                throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
            }
        } else {
            throw new CarNotFoundException(carDTO.getID());
        }
    }

    public CarDTO deleteCarAdmin(Integer id) {
        Optional<Car> carFounded = carRepository.findById(id);

        if (carFounded.isPresent()) {
            carRepository.deleteById(id);

            return CarAdapter.toDTO(carFounded.get());
        } else {
            throw new CarNotFoundException(id);
        }
    }



    protected boolean isUniqueVIN(String VIN) {
        Optional<Car> carFounded = carRepository.findByVIN(VIN);

        return !carFounded.isPresent();
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
