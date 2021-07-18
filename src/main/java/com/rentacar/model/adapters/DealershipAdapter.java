package com.rentacar.model.adapters;

import com.rentacar.model.DealershipDTO;
import com.rentacar.repository.dealership.Dealership;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DealershipAdapter {

    public static DealershipDTO toDTO(Dealership dealership) {
        DealershipDTO dealershipDTO = DealershipDTO.builder()
                .ID(dealership.getID())
                .name(dealership.getName())
                .city(CityAdapter.toDTO(dealership.getCity()))
                .country(CountryAdapter.toDTO(dealership.getCountry()))
                .email(dealership.getEmail())
                .phoneNumber(dealership.getPhoneNumber())
                .build();

        if (dealership.getCars() != null) {
            dealershipDTO.setCars(CarAdapter.toListDTO(dealership.getCars()));
        } else {
            dealershipDTO.setCars(Collections.emptyList());
        }

        return dealershipDTO;
    }

    public static Dealership fromDTO (DealershipDTO dealershipDTO) {
        Dealership dealership = Dealership.builder()
                .ID(dealershipDTO.getID())
                .name(dealershipDTO.getName())
                .city(CityAdapter.fromDTO(dealershipDTO.getCity()))
                .country(CountryAdapter.fromDTO(dealershipDTO.getCountry()))
                .email(dealershipDTO.getEmail())
                .phoneNumber(dealershipDTO.getPhoneNumber())
                .build();

        if (dealershipDTO.getCars() != null) {
            dealership.setCars(CarAdapter.fromListDTO(dealershipDTO.getCars()));
        } else {
            dealership.setCars(Collections.emptyList());
        }

        return dealership;
    }

    public static List<DealershipDTO> toListDTO (List<Dealership> dealershipList) {
        List<DealershipDTO> dealershipDTOList = new ArrayList<>();

        for (Dealership dealership : dealershipList) {
            dealershipDTOList.add(toDTO(dealership));
        }

        return dealershipDTOList;
    }

    public static List<Dealership> fromListDTO (List<DealershipDTO> dealershipDTOList) {
        List<Dealership> dealershipList = new ArrayList<>();

        for (DealershipDTO dealershipDTO : dealershipDTOList) {
            dealershipList.add(fromDTO(dealershipDTO));
        }

        return dealershipList;
    }
}
