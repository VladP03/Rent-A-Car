package com.rentacar;

import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.CarService;
import com.rentacar.service.exceptions.car.CarFirstRegistrationException;
import com.rentacar.service.exceptions.car.CarFuelException;
import com.rentacar.service.exceptions.car.CarGearboxException;
import com.rentacar.service.exceptions.car.CarNotFoundException;
import com.rentacar.service.validations.Car.CarValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TestCar {

    @Mock
    private CarRepository carRepositoryMock;
    @InjectMocks
    private CarService carService;

    private CarDTO carDTO;

    @BeforeEach
    private void init() {
        carDTO = CarDTO.builder()
                    .ID(null)
                    .brandName("TestBrandName")
                    .name("TestName")
                    .VIN("TestVIN")
                    .firstRegistration(Calendar.getInstance().get(Calendar.YEAR))
                    .engineCapacity(Calendar.getInstance().get(Calendar.YEAR))
                    .fuel("gas")
                    .mileage(0d)
                    .gearbox("manual")
                    .build();

        namesToUpper();
    }

    private void namesToUpper() {
        carDTO.setBrandName(carDTO.getBrandName().toUpperCase());
        carDTO.setName(carDTO.getName().toUpperCase());
        carDTO.setVIN(carDTO.getVIN().toUpperCase());
        carDTO.setFuel(carDTO.getFuel().toUpperCase());
        carDTO.setGearbox(carDTO.getGearbox().toUpperCase());
    }


    /* getCar() */

    @Test
    public void testCar_getCar_idAndBrandNameNull() {
        Mockito.when(carRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<CarDTO> carDTOList = carService.getListOfCars(null, null);

        Assertions.assertEquals(Collections.emptyList(), carDTOList);
    }

    @Test
    public void testCar_getCar_idNull() {
        Mockito.when(carRepositoryMock.findByBrandName(Mockito.anyString())).thenReturn(Optional.of(Collections.emptyList()));

        List<CarDTO> carDTOList = carService.getListOfCars(null, Mockito.anyString());

        Assertions.assertEquals(Collections.emptyList(), carDTOList);
    }

    @Test
    public void testCar_getCar_idNull_CarNotFound() {
        Mockito.when(carRepositoryMock.findByBrandName(Mockito.anyString())).thenReturn(Optional.empty());

        CarNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarNotFoundException.class,
                () -> carService.getListOfCars(null, carDTO.getBrandName()));

        Assertions.assertEquals("Car not found. In database does not exists an car with brand name " + carDTO.getBrandName() + ".", exception.getMessage());
    }

    @Test
    public void testCar_getCar_brandNameNull() {
        carDTO.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CarAdapter.fromDTO(carDTO)));

        List<CarDTO> carDTOList = carService.getListOfCars(carDTO.getID(), null);

        Assertions.assertEquals(Collections.singletonList(carDTO), carDTOList);
    }

    @Test
    public void testCar_getCar_brandNameNull_CarNotFound() {
        carDTO.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CarNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarNotFoundException.class,
                () -> carService.getListOfCars(carDTO.getID(), null));

        Assertions.assertEquals("Car not found. In database does not exists an car with id " + carDTO.getID() + ".", exception.getMessage());
    }

    @Test
    public void testCar_getCar_IdAndBrandNameNotNull() {
        carDTO.setID(1);

        Mockito.when(carRepositoryMock.findByIDAndBrandName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(CarAdapter.fromDTO(carDTO)));

        List<CarDTO> carDTOList = carService.getListOfCars(carDTO.getID(), carDTO.getBrandName());

        Assertions.assertEquals(Collections.singletonList(carDTO), carDTOList);
    }

    @Test
    public void testCar_getCar_IdAndBrandNameNotNull_CarNotFound() {
        carDTO.setID(1);

        Mockito.when(carRepositoryMock.findByIDAndBrandName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.empty());

        CarNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarNotFoundException.class,
                () -> carService.getListOfCars(carDTO.getID(), carDTO.getBrandName()));

        Assertions.assertEquals("Car not found. In database does not exists an car with id " + carDTO.getID() + " and brand name " + carDTO.getBrandName() + ".", exception.getMessage());
    }


    /* createCar() */

    @Test
    public void testCar_createCar_firstRegistrationViolated_Older() {
        carDTO.setFirstRegistration(1);

        CarFirstRegistrationException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.createCarAdmin(carDTO));

        Assertions.assertEquals("Car first registration can not be older than 10 years, year introduced: " + carDTO.getFirstRegistration() + ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    public void testCar_createCar_firstRegistrationViolated_Greater() {
        carDTO.setFirstRegistration(9999);

        CarFirstRegistrationException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.createCarAdmin(carDTO));

        Assertions.assertEquals("Car first registration can not be greater than current year, year introduced: " + carDTO.getFirstRegistration() + ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    public void testCar_createCar_fuelViolated() {
        carDTO.setFuel("TestFuel");

        CarFuelException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarFuelException.class,
                () -> carService.createCarAdmin(carDTO));

        Assertions.assertEquals("Car fuel is incorrect. Fuel introduced: " + carDTO.getFuel() + ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    public void testCar_createCar_gearboxViolated() {
        carDTO.setGearbox("TestGearbox");

        CarGearboxException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarGearboxException.class,
                () -> carService.createCarAdmin(carDTO));

        Assertions.assertEquals("Car gearbox is incorrect. Gearbox introduced: " + carDTO.getGearbox() +  ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }


    /* updateCar() */

    @Test
    public void testCar_updateCar_CarNotFoundException() {
        carDTO.setID(1);

        CarNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarNotFoundException.class,
                () -> carService.updateCarAdmin(carDTO));

        Assertions.assertEquals("Car not found. In database does not exists an car with id " + carDTO.getID() + ".", exception.getMessage());
    }

    @Test
    public void testCar_updateCar_firstRegistrationViolated_Older() {
        carDTO.setID(1);
        carDTO.setFirstRegistration(1);

        Mockito.when(carRepositoryMock.findByID(Mockito.anyInt())).thenReturn(true);
        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CarAdapter.fromDTO(carDTO)));

        CarFirstRegistrationException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.updateCarAdmin(carDTO));

        Assertions.assertEquals("Car first registration can not be older than 10 years, year introduced: " + carDTO.getFirstRegistration() + ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    public void testCar_updateCar_firstRegistrationViolated_Greater() {
        carDTO.setID(1);
        carDTO.setFirstRegistration(9999);

        Mockito.when(carRepositoryMock.findByID(Mockito.anyInt())).thenReturn(true);
        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CarAdapter.fromDTO(carDTO)));

        CarFirstRegistrationException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.updateCarAdmin(carDTO));

        Assertions.assertEquals("Car first registration can not be greater than current year, year introduced: " + carDTO.getFirstRegistration() + ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    public void testCar_updateCar_fuelViolated() {
        carDTO.setID(1);
        carDTO.setFuel("testFuel");

        Mockito.when(carRepositoryMock.findByID(Mockito.anyInt())).thenReturn(true);
        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CarAdapter.fromDTO(carDTO)));

        CarFuelException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarFuelException.class,
                () -> carService.updateCarAdmin(carDTO));

        Assertions.assertEquals("Car fuel is incorrect. Fuel introduced: " + carDTO.getFuel() + ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    public void testCar_updateCar_gearboxViolated() {
        carDTO.setID(1);
        carDTO.setGearbox("testGearbox");

        Mockito.when(carRepositoryMock.findByID(Mockito.anyInt())).thenReturn(true);
        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CarAdapter.fromDTO(carDTO)));

        CarGearboxException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarGearboxException.class,
                () -> carService.updateCarAdmin(carDTO));

        Assertions.assertEquals("Car gearbox is incorrect. Gearbox introduced: " + carDTO.getGearbox() +  ". Error on the following car: " + carDTO.getBrandName() + " " + carDTO.getName() + ", with VIN: " + carDTO.getVIN() + ".", exception.getMessage());
    }


    /* patchCar() */

    @Test
    public void testCar_patchCar_CarNotFoundException() {
        carDTO.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CarNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarNotFoundException.class,
                () -> carService.patchCarAdmin(carDTO));

        Assertions.assertEquals("Car not found. In database does not exists an car with id " + carDTO.getID() + ".", exception.getMessage());
    }


    /* deleteCar() */

    @Test
    public void testCar_deleteCar_CarNotFoundException() {
        carDTO.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CarNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CarNotFoundException.class,
                () -> carService.deleteCarAdmin(carDTO));

        Assertions.assertEquals("Car not found. In database does not exists an car with id " + carDTO.getID() + ".", exception.getMessage());
    }
}
