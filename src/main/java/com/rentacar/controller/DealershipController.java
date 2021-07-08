package com.rentacar.controller;

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
    private ResponseEntity<List<DealershipDTO>> getAll() {
        return ResponseEntity.ok(dealershipService.getAll());
    }

    @PostMapping("/add")
    private ResponseEntity<DealershipDTO> createDealership(@Valid @RequestBody DealershipDTO dealershipDTO) {
        return ResponseEntity.ok(dealershipService.createDealership(dealershipDTO));
    }
}
