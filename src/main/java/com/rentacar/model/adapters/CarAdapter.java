package com.rentacar.model.adapters;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.repository.car.Car;

public class CarAdapter {

    public static CarDTO toDTO(Car car) {
        return new CarDTO()
                .setID(car.getID())
                .setBrandName(car.getBrandName())
                .setName(car.getName())
                .setFirstRegistration(car.getFirstRegistration())
                .setEngineCapacity(car.getEngineCapacity())
                .setHorsePower(car.getHorsePower())
                .setFuel(car.getFuel())
                .setMileage(car.getMileage())
                .setGearbox(car.getGearbox());
    }

    public static Car fromDTO (CarDTO carDTO) {
        return new Car()
                .setID(carDTO.getID())
                .setBrandName(carDTO.getBrandName())
                .setName(carDTO.getName())
                .setFirstRegistration(carDTO.getFirstRegistration())
                .setEngineCapacity(carDTO.getEngineCapacity())
                .setHorsePower(carDTO.getHorsePower())
                .setFuel(carDTO.getFuel())
                .setMileage(carDTO.getMileage())
                .setGearbox(carDTO.getGearbox());
    }

    public static List<CarDTO> toListDTO (List<Car> carList) {
        List<CarDTO> carDTOList = new ArrayList<>();

        for (Car car : carList) {
            carDTOList.add(toDTO(car));
        }

        return carDTOList;
    }

    public static List<Car> fromListDTO (List<CarDTO> carDTOList) {
        List<Car> carList = new ArrayList<>();

        for (CarDTO carDTO : carDTOList) {
            carList.add(fromDTO(carDTO));
        }

        return carList;
    }
}
