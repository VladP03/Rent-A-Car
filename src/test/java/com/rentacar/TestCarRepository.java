package com.rentacar;

import java.util.*;
import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.CarService;
import com.rentacar.service.exceptions.car.*;
import org.junit.jupiter.api.Assertions;
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
    public void init() {
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
    public void testCarAdd_ValidParameters() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(null);
        Mockito.when(carRepositoryMock.save(Mockito.any(Car.class))).thenReturn(baseCar);

        CarDTO carAdded = carService.createCar(baseCarDTO);

        assertEquals(baseCarDTO, carAdded);
    }

    @Test
    public void testCarAdd_AlreadyExistsException() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(baseCar);

        CarAlreadyExistsException exception = assertThrows(
                CarAlreadyExistsException.class,
                () -> carService.createCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car already exists in db."));
    }

    @Test
    public void testCarAdd_FirstRegistrationException_CarTooOld() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(null);

        baseCarDTO.setFirstRegistration(1999);

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.createCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car firstRegistration can not be older than 10 years"));
    }

    @Test
    public void testCarAdd_FirstRegistrationException_CarRegGreatherThenCurrentYear() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(null);

        baseCarDTO.setFirstRegistration(9999);

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.createCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car firstRegistration can not be greater than current year"));
    }

    @Test
    public void testCarAdd_CarFuelException_InvalidParameter() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(null);

        baseCarDTO.setFuel("Test");

        CarFuelException exception = assertThrows(
                CarFuelException.class,
                () -> carService.createCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car fuel type is incorrect!"));
    }

    @Test
    public void testCarAdd_CarGearboxException_InvalidParameter() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(null);

        baseCarDTO.setGearbox("Test");

        CarGearboxException exception = assertThrows(
                CarGearboxException.class,
                () -> carService.createCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car gearbox is incorrect!"));
    }

    @Test
    public void testCarGet_CarNotFoundException_IdNotInDB() {
        baseCarDTO.setID(1);

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.getCar(baseCarDTO.getID())
        );

        assertTrue(exception.getMessage().contains("The car with ID " + baseCarDTO.getID() + " doesn't exists in database."));
    }

    @Test
    public void testCarUpdate_Invalid() {

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.updateCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("The car with ID " + baseCarDTO.getID() + " doesn't exists in database."));
    }

    @Test
    public void testCarDelete_CarDoesNotExistsInDB() {

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.deleteCar(Mockito.anyInt())
        );

        assertTrue(exception.getMessage().contains("The car with ID " + 0 + " doesn't exists in database."));
    }

    @Test
    public void testCarDelete_ValidTest() {
        baseCarDTO.setID(1);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarDTO carDTODeleted = carService.deleteCar(baseCarDTO.getID());

        assertEquals(carDTODeleted, baseCarDTO);
    }

    @Test
    public void testCarPatch_ValidTest() {
        baseCarDTO.setID(1);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        baseCar.setName("NewCar");
        baseCar.setBrandName("NewCar");

        CarDTO patchedCarDTO = carService.pathcCar(baseCarDTO);

        assertEquals(patchedCarDTO, baseCarDTO);
    }

    @Test
    public void TestCarPatch_CarNotFound() {

        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("The car with ID " + baseCarDTO.getID() + " doesn't exists in database."));
    }

    @Test
    public void TestCarPatch_FirstRegistrationException_Older() {
        baseCarDTO.setID(1);
        baseCarDTO.setFirstRegistration(1);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car firstRegistration can not be older than 10 years"));
    }

    @Test
    public void TestCarPatch_FirstRegistrationException_Greater() {
        baseCarDTO.setID(1);
        baseCarDTO.setFirstRegistration(9999);
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarFirstRegistrationException exception = assertThrows(
                CarFirstRegistrationException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car firstRegistration can not be greater than current year"));
    }

    @Test
    public void TestCarPatch_FuelException() {
        baseCarDTO.setID(1);
        baseCarDTO.setFirstRegistration(2020);
        baseCarDTO.setFuel("TEST");
        baseCar = CarAdapter.fromDTO(baseCarDTO);

        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(baseCar));

        CarFuelException exception = assertThrows(
                CarFuelException.class,
                () -> carService.pathcCar(baseCarDTO)
        );

        assertTrue(exception.getMessage().contains("Car fuel type is incorrect!"));
    }

    @Test
    public void TestCarPatch_GearboxException() {
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

        assertTrue(exception.getMessage().contains("Car gearbox is incorrect!"));
    }
}
