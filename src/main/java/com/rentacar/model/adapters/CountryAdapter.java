package com.rentacar.model.adapters;

import com.rentacar.model.CountryDTO;
import com.rentacar.repository.country.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountryAdapter {

    public static CountryDTO toDTO(Country country) {
        CountryDTO countryDTO = CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .phoneNumber(country.getPhoneNumber())
                .build();

        if (country.getCityList() != null) {
            countryDTO.setCityList(CityAdapter.toListDTO(country.getCityList()));
        } else {
            countryDTO.setCityList(Collections.emptyList());
        }

        return countryDTO;
    }

    public static Country fromDTO (CountryDTO countryDTO) {
        Country country = Country.builder()
                .id(countryDTO.getId())
                .name(countryDTO.getName())
                .phoneNumber(countryDTO.getPhoneNumber())
                .build();

        if (countryDTO.getCityList() != null) {
            country.setCityList(CityAdapter.fromListDTO(countryDTO.getCityList()));
        } else {
            country.setCityList(Collections.emptyList());
        }

        return country;
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
