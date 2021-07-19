package com.rentacar.controller;

import com.rentacar.model.DealershipDTO;
import com.rentacar.model.RentDTO;
import com.rentacar.service.RentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/v1/rent")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    private ResponseEntity<List<RentDTO>> getRent() {
        return ResponseEntity.ok(rentService.getRent());
    }

    @PostMapping("/{dealershipId}")
    private ResponseEntity<RentDTO> createRent(@PathVariable Integer dealershipId, @Valid @RequestBody RentDTO rentDTO) {
        return ResponseEntity.ok(rentService.createRent(dealershipId, rentDTO));
    }

    @PutMapping
    private ResponseEntity<RentDTO> updateRent(@Valid RentDTO rentDTO) {
        return ResponseEntity.ok(rentService.updateRent(rentDTO));
    }

    @PatchMapping
    private ResponseEntity<RentDTO> patchRent(@Valid RentDTO rentDTO) {
        return ResponseEntity.ok(rentService.patchRent(rentDTO));
    }

    @DeleteMapping("/rent/{id}")
    private ResponseEntity<RentDTO> deleteRent(@PathVariable Integer id) {
        return ResponseEntity.ok(rentService.deleteRent(id));
    }
}
