package com.rentacar.controller;

import com.rentacar.model.CountryDTO;
import com.rentacar.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/v1/country")
public class CountryController
{
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    private ResponseEntity<List<CountryDTO>> getCountry(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(countryService.getCountry(id, name));
    }

    @PostMapping
    private ResponseEntity<CountryDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryService.createCountry(countryDTO));
    }

    @PutMapping
    private ResponseEntity<CountryDTO> updateCountry(@Valid @RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryService.updateCountry(countryDTO));
    }

    @PatchMapping
    private ResponseEntity<CountryDTO> patchCountry(@RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryService.patchCountry(countryDTO));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<CountryDTO> deleteCountry(@PathVariable Integer id) {
        return ResponseEntity.ok(countryService.deleteCountry(id));
    }
}
