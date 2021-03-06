package com.rentacar.model.adapters;

import com.rentacar.model.CountryDTO;
import com.rentacar.repository.country.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter {

    public static CountryDTO toDTO(Country country) {
        CountryDTO countryDTO = new CountryDTO();

        countryDTO.setId(country.getId());
        countryDTO.setName(country.getName());
        countryDTO.setPhoneNumber(country.getPhoneNumber());
        countryDTO.setCityList(CityAdapter.toListDTO(country.getCityList()));

        return countryDTO;
    }

    public static Country fromDTO (CountryDTO countryDTO) {
        return Country.builder()
                .id(countryDTO.getId())
                .name(countryDTO.getName())
                .phoneNumber(countryDTO.getPhoneNumber())
                .cityList(CityAdapter.fromListDTO(countryDTO.getCityList()))
                .build();
    }

    public static List<CountryDTO> toListDTO (List<Country> countryList) {
        List<CountryDTO> countryDTOList = new ArrayList<>();

        for (Country country : countryList) {
            countryDTOList.add(toDTO(country));
        }

        return countryDTOList;
    }

    public static List<Country> fromListDTO (List<CountryDTO> countryDTOList) {
        List<Country> countryList = new ArrayList<>();

        for (CountryDTO countryDTO : countryDTOList) {
            countryList.add(fromDTO(countryDTO));
        }

        return countryList;
    }
}
