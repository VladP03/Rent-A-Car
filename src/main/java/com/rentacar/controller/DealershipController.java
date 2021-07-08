package com.rentacar.controller;

import com.rentacar.service.DealershipService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dealership")
public class DealershipController {

    private final DealershipService dealershipService;

    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }
}
