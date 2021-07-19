package com.rentacar.model.adapters;

import com.rentacar.model.RentDTO;
import com.rentacar.repository.rent.Rent;

import java.util.ArrayList;
import java.util.List;

public class RentAdapter {

    public static RentDTO toDTO(Rent rent) {
        return RentDTO.builder()
                .ID(rent.getID())
                .car(CarAdapter.toDTO(rent.getCar()))
                .startDate(rent.getStartDate())
                .endDate(rent.getEndDate())
                .build();
    }

    public static Rent fromDTO (RentDTO rentDTO) {
        return Rent.builder()
                .ID(rentDTO.getID())
                .car(CarAdapter.fromDTO(rentDTO.getCar()))
                .startDate(rentDTO.getStartDate())
                .endDate(rentDTO.getEndDate())
                .build();
    }

    public static List<RentDTO> toListDTO (List<Rent> rentList) {
        List<RentDTO> rentDTOList = new ArrayList<>();

        for (Rent rent : rentList) {
            rentDTOList.add(toDTO(rent));
        }

        return rentDTOList;
    }

    public static List<Rent> fromListDTO (List<RentDTO> rentDTOList) {
        List<Rent> rentList = new ArrayList<>();

        for (RentDTO rentDTO : rentDTOList) {
            rentList.add(fromDTO(rentDTO));
        }

        return rentList;
    }
}
