package com.rentacar.controller;

import com.rentacar.model.CarDTO;
import com.rentacar.model.DealershipDTO;
import com.rentacar.service.DealershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/dealership")
public class DealershipController {

    private final DealershipService dealershipService;

    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    @GetMapping
    private ResponseEntity<List<DealershipDTO>> getDealership(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(dealershipService.getDealership(id, name));
    }

    @PostMapping
    private ResponseEntity<DealershipDTO> createDealership(@Valid @RequestBody DealershipDTO dealershipDTO) {
        return ResponseEntity.ok(dealershipService.createDealership(dealershipDTO));
    }

    @PutMapping
    private ResponseEntity<DealershipDTO> updateDealership(@Valid @RequestBody DealershipDTO dealershipDTO) {
        return ResponseEntity.ok(dealershipService.updateDealership(dealershipDTO));
    }

    @PatchMapping
    private ResponseEntity<DealershipDTO> patchDealership(@RequestBody DealershipDTO dealershipDTO) {
        return ResponseEntity.ok(dealershipService.patchDealership(dealershipDTO));
    }

    @PatchMapping("/{id}")
    private ResponseEntity<DealershipDTO> patchDealershipAddCars(@PathVariable Integer id, @RequestBody List<CarDTO> carList) {
        return ResponseEntity.ok(dealershipService.patchDealershipAddCars(id, carList));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<DealershipDTO> deleteDealership(@PathVariable Integer id) {
        return ResponseEntity.ok(dealershipService.deleteDealership(id));
    }
}
