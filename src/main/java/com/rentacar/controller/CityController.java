package com.rentacar.controller;

import com.rentacar.model.CityDTO;
import com.rentacar.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("v1/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    private ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCountries());
    }

    @PostMapping
    private ResponseEntity<CityDTO> createCity(@Valid @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.createCity(cityDTO));
    }

    @PutMapping
    private ResponseEntity<CityDTO> updateCity(@Valid @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.updateCity(cityDTO));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<CityDTO> deleteCity(@PathVariable Integer id) {
        return ResponseEntity.ok(cityService.deleteCity(id));
    }
}
