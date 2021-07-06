package com.rentacar.controller;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    private ResponseEntity<List<CarDTO>> getAll() {
        return ResponseEntity.ok(carService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<CarDTO> getCar(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCar(id));
    }

    @GetMapping("/brand/{brandName}")
    private ResponseEntity<List<CarDTO>> getCarsWithBrandName(@PathVariable String brandName) {
        return ResponseEntity.ok(carService.getCarsWithBrandName(brandName));
    }
}
