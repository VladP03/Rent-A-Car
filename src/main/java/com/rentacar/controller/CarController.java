package com.rentacar.controller;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    private ResponseEntity<List<CarDTO>> getCar(@RequestParam(required = false) Integer id, @RequestParam(required = false) String brandName ) {
        return ResponseEntity.ok(carService.getCar(id, brandName));
    }

    @PostMapping
    private ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.createCarAdmin(carDTO));
    }

    @PutMapping
    private ResponseEntity<CarDTO> updateCar(@Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateCar(carDTO));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<CarDTO> deleteCar(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.deleteCar(id));
    }

    @PatchMapping
    private ResponseEntity<CarDTO> patchCar(@RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.patchCar(carDTO));
    }
}
