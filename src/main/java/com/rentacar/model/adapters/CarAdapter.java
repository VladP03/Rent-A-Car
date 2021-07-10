package com.rentacar.model.adapters;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.repository.car.Car;

public class CarAdapter {

    public static CarDTO toDTO(Car car) {
        return CarDTO.builder()
                .ID(car.getID())
                .brandName(car.getBrandName())
                .name(car.getName())
                .VIN(car.getVIN())
                .firstRegistration(car.getFirstRegistration())
                .engineCapacity(car.getEngineCapacity())
                .fuel(car.getFuel())
                .mileage(car.getMileage())
                .gearbox(car.getGearbox())
                .build();
    }

    public static Car fromDTO (CarDTO carDTO) {
        return Car.builder()
                .ID(carDTO.getID())
                .brandName(carDTO.getBrandName())
                .name(carDTO.getName())
                .VIN(carDTO.getVIN())
                .firstRegistration(carDTO.getFirstRegistration())
                .engineCapacity(carDTO.getEngineCapacity())
                .fuel(carDTO.getFuel())
                .mileage(carDTO.getMileage())
                .gearbox(carDTO.getGearbox())
                .build();
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
