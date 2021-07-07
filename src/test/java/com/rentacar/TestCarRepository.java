package com.rentacar;


import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.CarService;
import com.rentacar.service.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TestCarRepository {

    private CarDTO baseCarDTO;
    private Car baseCar;

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

        baseCar = CarAdapter.fromDTO(baseCarDTO);
    }

    @Mock
    private CarRepository carRepositoryMock;
    @InjectMocks
    private CarService carService;

    @Test
    public void testCarAdd_ValidParameters() {
        Mockito.when(carRepositoryMock.findByVIN(Mockito.anyString())).thenReturn(null);
        Mockito.when(carRepositoryMock.save(Mockito.any(Car.class))).thenReturn(baseCar);

        carService.createCar(baseCarDTO);
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
}
