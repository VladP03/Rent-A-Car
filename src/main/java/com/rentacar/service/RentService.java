package com.rentacar.service;

import com.rentacar.model.DealershipDTO;
import com.rentacar.model.RentDTO;
import com.rentacar.model.adapters.RentAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.repository.rent.RentRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Validated
public class RentService {

    private final RentRepository rentRepository;

    private final DealershipService dealershipService;



    public RentService(RentRepository rentRepository, DealershipService dealershipService) {
        this.rentRepository = rentRepository;
        this.dealershipService = dealershipService;
    }



    public List<RentDTO> getRent() {
        return RentAdapter.toListDTO(rentRepository.findAll());
    }

    @Validated(OnCreate.class)
    public RentDTO createRent(Integer dealershipId, @Valid RentDTO rentDTO) {
        List<DealershipDTO> dealershipFounded = dealershipService.getDealership(dealershipId, null);

        if (dealershipFounded.size() > 0) {
            // start_date < end_date
            return RentAdapter.toDTO(rentRepository.save(RentAdapter.fromDTO(rentDTO)));

        } else {
            throw new RuntimeException();
        }
    }

    public RentDTO updateRent(RentDTO rentDTO) {
        return null;
    }

    public RentDTO patchRent(RentDTO rentDTO) {
        return null;
    }

    public RentDTO deleteRent(Integer id) {
        return null;
    }



    private boolean checkDate (SimpleDateFormat start_date, SimpleDateFormat end_date) {
        // bla bla

        return true;
    }
}
