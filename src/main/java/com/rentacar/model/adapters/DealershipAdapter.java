package com.rentacar.model.adapters;

import com.rentacar.model.DealershipDTO;
import com.rentacar.repository.dealership.Dealership;

import java.util.ArrayList;
import java.util.List;

public class DealershipAdapter {

    public static DealershipDTO toDTO(Dealership dealership) {
        DealershipDTO dealershipDTO = new DealershipDTO();

        dealershipDTO.setID(dealership.getID());
        dealershipDTO.setName(dealership.getName());
        dealershipDTO.setCity(CityAdapter.toDTO(dealership.getCity()));
        dealershipDTO.setCountry(CountryAdapter.toDTO(dealership.getCountry()));
        dealershipDTO.setEmail(dealership.getEmail());
        dealershipDTO.setPhoneNumber(dealership.getPhoneNumber());
        dealershipDTO.setCars(CarAdapter.toListDTO(dealership.getCars()));

        return dealershipDTO;
    }

    public static Dealership fromDTO (DealershipDTO dealershipDTO) {
        return Dealership.builder()
                .ID(dealershipDTO.getID())
                .name(dealershipDTO.getName())
                .city(CityAdapter.fromDTO(dealershipDTO.getCity()))
                .country(CountryAdapter.fromDTO(dealershipDTO.getCountry()))
                .email(dealershipDTO.getEmail())
                .phoneNumber(dealershipDTO.getPhoneNumber())
                .cars(CarAdapter.fromListDTO(dealershipDTO.getCars()))
                .build();
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
