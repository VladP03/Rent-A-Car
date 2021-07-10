package com.rentacar.model.adapters;

import com.rentacar.model.CityDTO;
import com.rentacar.repository.city.City;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter {

    public static CityDTO toDTO(City city) {
        return CityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .build();

    }

    public static City fromDTO (CityDTO cityDTO) {
        return City.builder()
                .id(cityDTO.getId())
                .name(cityDTO.getName())
                .build();
    }

    public static List<CityDTO> toListDTO (List<City> cityList) {
        List<CityDTO> cityDTOList = new ArrayList<>();

        for (City city : cityList) {
            cityDTOList.add(toDTO(city));
        }

        return cityDTOList;
    }

    public static List<City> fromListDTO (List<CityDTO> cityDTOList) {
        List<City> cityList = new ArrayList<>();

        for (CityDTO cityDTO : cityDTOList) {
            cityList.add(fromDTO(cityDTO));
        }

        return cityList;
    }
}
