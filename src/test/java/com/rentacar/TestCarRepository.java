package com.rentacar;


import com.rentacar.model.CarDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.repository.car.Car;
import com.rentacar.repository.car.CarRepository;
import com.rentacar.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestCarRepository {
    private final CarDTO baseCarDTO = new CarDTO()
            .setID(1)
            .setBrandName("Test")
            .setName("Test")
            .setVIN("Test")
            .setFirstRegistration(1999)
            .setEngineCapacity(1)
            .setFuel("test")
            .setMileage(0d)
            .setGearbox("test");

    private final Car baseCar = CarAdapter.fromDTO(baseCarDTO);

    @Mock
    private CarRepository carRepositoryMock;
    @InjectMocks
    private CarService carService;

    @Test
    public void testCarAdd() {

    }
}
