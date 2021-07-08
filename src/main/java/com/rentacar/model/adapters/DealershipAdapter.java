package com.rentacar.model.adapters;

import com.rentacar.model.DealershipDTO;
import com.rentacar.repository.dealership.Dealership;

import java.util.ArrayList;
import java.util.List;

public class DealershipAdapter {

    public static DealershipDTO toDTO(Dealership dealership) {
        return new DealershipDTO()
                .setID(dealership.getID())
                .setName(dealership.getName())
                .setCity(dealership.getCity())
                .setCountry(dealership.getCountry())
                .setEmail(dealership.getEmail())
                .setPhoneNumber(dealership.getPhoneNumber())
                .setCars(dealership.getCars());
    }

    public static Dealership fromDTO (DealershipDTO dealershipDTO) {
        return new Dealership()
                .setID(dealershipDTO.getID())
                .setName(dealershipDTO.getName())
                .setCity(dealershipDTO.getCity())
                .setCountry(dealershipDTO.getCountry())
                .setEmail(dealershipDTO.getEmail())
                .setPhoneNumber(dealershipDTO.getPhoneNumber())
                .setCars(dealershipDTO.getCars());
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
