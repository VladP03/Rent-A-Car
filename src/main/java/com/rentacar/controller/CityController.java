package com.rentacar.controller;

import com.rentacar.model.CityDTO;
import com.rentacar.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    private ResponseEntity<List<CityDTO>> getCity(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(cityService.getCity(id, name));
    }

    @PostMapping
    private ResponseEntity<CityDTO> createCity(@Valid @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.createCityAdmin(cityDTO));
    }

    @PutMapping
    private ResponseEntity<CityDTO> updateCity(@Valid @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.updateCityAdmin(cityDTO));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<CityDTO> deleteCity(@PathVariable Integer id) {
        return ResponseEntity.ok(cityService.deleteCityAdmin(id));
    }
}
