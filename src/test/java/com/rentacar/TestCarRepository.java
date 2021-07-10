package com.rentacar;

import java.util.*;
import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.CarService;
import com.rentacar.service.exceptions.car.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestCarRepository {

    private CarDTO baseCarDTO;
    private Car baseCar;

    @Mock
    private CarRepository carRepositoryMock;
    @InjectMocks
    private CarService carService;

    @BeforeEach
    void init() {
        baseCarDTO = new CarDTO()
                .setID(null)
                .setBrandName("Test")
                .setName("Test")
                .setVIN("Test")
                .setFirstRegistration(2018)
                .setEngineCapacity(2000)
                .setFuel("gas")
                .setMileage(0d)
                .setGearbox("manual");

        namesToUpper(baseCarDTO);

        baseCar = CarAdapter.fromDTO(baseCarDTO);
    }

    private void namesToUpper(CarDTO carDTO) {
        carDTO.setBrandName(carDTO.getBrandName().toUpperCase());
        carDTO.setName(carDTO.getName().toUpperCase());
        carDTO.setVIN(carDTO.getVIN().toUpperCase());
        carDTO.setFuel(carDTO.getFuel().toUpperCase());
        carDTO.setGearbox(carDTO.getGearbox().toUpperCase());
    }



    @Test
    void testCarAdd_ValidParameters() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(carRepositoryMock.save(Mockito.any(Car.class))).thenReturn(baseCar);

        CarDTO carAdded = carService.createCarAdmin(baseCarDTO);

        assertEquals(baseCarDTO, carAdded);
    }

    @Test
    void testCarAdd_AlreadyExistsException() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(Optional.ofNullable(baseCar));

        CarAlreadyExistsException exception = assertThrows(
                CarAlreadyExistsException.class,
                () -> carService.createCarAdmin(baseCarDTO)
        );

        assertEquals("Car already exists. Error on the following car: " + baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarAdd_FirstRegistrationException_Older() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(Optional.empty());

        baseCarDTO.setFirstRegistration(1999);

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.createCarAdmin(baseCarDTO)
        );

        assertEquals("Car first registration can not be older than 10 years, year introduced: " + baseCarDTO.getFirstRegistration() +
                ". Error on the following car: " + baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarAdd_FirstRegistrationException_Greater() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(Optional.empty());

        baseCarDTO.setFirstRegistration(9999);

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.createCarAdmin(baseCarDTO)
        );

        assertEquals("Car first registration can not be greater than current year, year introduced: " + baseCarDTO.getFirstRegistration() +
                ". Error on the following car: " + baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarAdd_CarFuelException_InvalidParameter() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(Optional.empty());

        baseCarDTO.setFuel("Test");

        CarFuelException exception = assertThrows(
                CarFuelException.class,
                () -> carService.createCarAdmin(baseCarDTO)
        );

        assertEquals("Car fuel is incorrect. Fuel introduced: " + baseCarDTO.getFuel() + ". Error on the following car: " +
                baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarAdd_CarGearboxException_InvalidParameter() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(Optional.empty());

        baseCarDTO.setGearbox("Test");

        CarGearboxException exception = assertThrows(
                CarGearboxException.class,
                () -> carService.createCarAdmin(baseCarDTO)
        );

        assertEquals("Car gearbox is incorrect. Gearbox introduced: " + baseCarDTO.getGearbox() + ". Error on the following car: " +
                baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarGet_CarNotFoundException_IdNotInDB() {
        baseCarDTO.setID(1);

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.getCar(baseCarDTO.getID())
        );

        assertEquals("Car not found. In database does not exists an car with id " + baseCarDTO.getID() + ".", exception.getMessage());
    }

    @Test
    void testCarUpdate_IdNotInDB() {

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.updateCar(baseCarDTO)
        );

        assertEquals("Car not found. In database does not exists an car with id " + baseCarDTO.getID() + ".", exception.getMessage());
    }

    @Test
    void testCarUpdate_FirstRegistrationException_Older() {
        baseCarDTO.setID(1);
        baseCar.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        baseCarDTO.setFirstRegistration(1999);

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.updateCar(baseCarDTO)
        );

        assertEquals("Car first registration can not be older than 10 years, year introduced: " + baseCarDTO.getFirstRegistration() +
                ". Error on the following car: " + baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarUpdate_FirstRegistrationException_Greater() {
        baseCarDTO.setID(1);
        baseCar.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        baseCarDTO.setFirstRegistration(9999);

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.updateCar(baseCarDTO)
        );

        assertEquals("Car first registration can not be greater than current year, year introduced: " + baseCarDTO.getFirstRegistration() +
                ". Error on the following car: " + baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarUpdate_FuelException () {
        baseCarDTO.setID(1);
        baseCar.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        baseCarDTO.setFuel("Test");

        CarFuelException exception = assertThrows(
                CarFuelException.class,
                () -> carService.updateCar(baseCarDTO)
        );

        assertEquals("Car fuel is incorrect. Fuel introduced: " + baseCarDTO.getFuel() + ". Error on the following car: " +
                baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarUpdate_GearboxException () {
        baseCarDTO.setID(1);
        baseCar.setID(1);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        baseCarDTO.setGearbox("Test");

        CarGearboxException exception = assertThrows(
                CarGearboxException.class,
                () -> carService.updateCar(baseCarDTO)
        );

        assertEquals("Car gearbox is incorrect. Gearbox introduced: " + baseCarDTO.getGearbox() + ". Error on the following car: " +
                baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void testCarDelete_CarDoesNotExistsInDB() {

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.deleteCar(Mockito.anyInt())
        );

        assertEquals("Car not found. In database does not exists an car with id " + 0 + ".", exception.getMessage());
    }

    @Test
    void testCarDelete_ValidTest() {
        baseCarDTO.setID(1);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarDTO carDTODeleted = carService.deleteCar(baseCarDTO.getID());

        assertEquals(carDTODeleted, baseCarDTO);
    }

    @Test
    void testCarPatch_ValidTest() {
        baseCarDTO.setID(1);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        baseCar.setName("NewCar");
        baseCar.setBrandName("NewCar");

        CarDTO patchedCarDTO = carService.pathcCar(baseCarDTO);

        assertEquals(patchedCarDTO, baseCarDTO);
    }

    @Test
    void TestCarPatch_CarNotFound() {

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertEquals("Car not found. In database does not exists an car with id " + baseCarDTO.getID() + ".", exception.getMessage());
    }

    @Test
    void TestCarPatch_FirstRegistrationException_Older() {
        baseCarDTO.setID(1);
        baseCarDTO.setFirstRegistration(1);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertEquals("Car first registration can not be older than 10 years, year introduced: " + baseCarDTO.getFirstRegistration() +
                ". Error on the following car: " + baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void TestCarPatch_FirstRegistrationException_Greater() {
        baseCarDTO.setID(1);
        baseCarDTO.setFirstRegistration(9999);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertEquals("Car first registration can not be greater than current year, year introduced: " + baseCarDTO.getFirstRegistration() +
                ". Error on the following car: " + baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void TestCarPatch_FuelException() {
        baseCarDTO.setID(1);
        baseCarDTO.setFirstRegistration(2020);
        baseCarDTO.setFuel("TEST");
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarFuelException exception = assertThrows(
                CarFuelException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertEquals("Car fuel is incorrect. Fuel introduced: " + baseCarDTO.getFuel() + ". Error on the following car: " +
                baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }

    @Test
    void TestCarPatch_GearboxException() {
        baseCarDTO.setID(1);
        baseCarDTO.setFirstRegistration(2020);
        baseCarDTO.setFuel("GAS");
        baseCarDTO.setGearbox("TEST");
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarGearboxException exception = assertThrows(
                CarGearboxException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertEquals("Car gearbox is incorrect. Gearbox introduced: " + baseCarDTO.getGearbox() + ". Error on the following car: " +
                baseCarDTO.getBrandName() + " " + baseCarDTO.getName() + ", with VIN: " + baseCarDTO.getVIN() + ".", exception.getMessage());
    }
}
