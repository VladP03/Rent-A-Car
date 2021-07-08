package com.rentacar.service;

import com.rentacar.repository.dealership.DealershipRepository;
import org.springframework.stereotype.Service;

@Service
public class DealershipService {

    private final DealershipRepository dealershipRepository;

    public DealershipService(DealershipRepository dealershipRepository) {
        this.dealershipRepository = dealershipRepository;
    }
}
