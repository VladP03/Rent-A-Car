package com.rentacar;

import com.rentacar.model.CityDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.repository.city.CityRepository;
import com.rentacar.service.CityService;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestCity {

    @Mock
    CityRepository cityRepositoryMock;
    @InjectMocks
    CityService cityService;

    CityDTO cityDTO;

    @BeforeEach
    public void init() {
        cityDTO = CityDTO.builder()
                .id(null)
                .name("TestName")
                .build();

        namesToUpper();
    }

    private void namesToUpper() {
        cityDTO.setName(cityDTO.getName().toUpperCase());
    }


    /* getCity() */

    @Test
    public void testCity_getCity_idAndNameNull() {
        Mockito.when(cityRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<CityDTO> cityDTOList = cityService.getCity(null, null);

        Assertions.assertEquals(Collections.emptyList(), cityDTOList);
    }

    @Test
    public void testCity_getCity_idNull() {
        Mockito.when(cityRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.of(CityAdapter.fromDTO(cityDTO)));

        List<CityDTO> cityDTOList = cityService.getCity(null, Mockito.anyString());

        Assertions.assertEquals(Collections.singletonList(cityDTO), cityDTOList);
    }

    @Test
    public void testCity_getCity_idNull_CityNotFound() {
        Mockito.when(cityRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        CityNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CityNotFoundException.class,
                () -> cityService.getCity(null, cityDTO.getName()));

        Assertions.assertEquals("City not found. In database does not exists an city with name " + cityDTO.getName() + ".", exception.getMessage());
    }

    @Test
    public void testCity_getCity_nameNull() {
        cityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CityAdapter.fromDTO(cityDTO)));

        List<CityDTO> cityDTOList = cityService.getCity(cityDTO.getId(), null);

        Assertions.assertEquals(Collections.singletonList(cityDTO), cityDTOList);
    }

    @Test
    public void testCity_getCity_nameNull_CityNotFound() {
        cityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CityNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CityNotFoundException.class,
                () -> cityService.getCity(cityDTO.getId(), null));

        Assertions.assertEquals("City not found. In database does not exists an city with id " + cityDTO.getId() + ".", exception.getMessage());
    }

    @Test
    public void testCity_getCity_IdAndNameNotNull() {
        cityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findByIdAndName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(CityAdapter.fromDTO(cityDTO)));

        List<CityDTO> cityDTOList = cityService.getCity(cityDTO.getId(), cityDTO.getName());

        Assertions.assertEquals(Collections.singletonList(cityDTO), cityDTOList);
    }

    @Test
    public void testCity_getCity_IdAndNameNotNull_CityNotFound() {
        cityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findByIdAndName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.empty());

        CityNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CityNotFoundException.class,
                () -> cityService.getCity(cityDTO.getId(), cityDTO.getName()));

        Assertions.assertEquals("City not found. In database does not exists an city with id " + cityDTO.getId() + " and name " + cityDTO.getName() + ".", exception.getMessage());
    }


    /* updateCity() */

    @Test
    public void testCity_updateCity_CityNotFound() {
        cityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CityNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CityNotFoundException.class,
                () -> cityService.updateCityAdmin(cityDTO)
        );

        Assertions.assertEquals("City not found. In database does not exists an city with id " + cityDTO.getId() + ".", exception.getMessage());
    }


    /* deleteCity() */

    @Test
    public void testCity_deleteCity_CityNotFound() {
        cityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CityNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CityNotFoundException.class,
                () -> cityService.deleteCityAdmin(cityDTO.getId())
        );

        Assertions.assertEquals("City not found. In database does not exists an city with id " + cityDTO.getId() + ".", exception.getMessage());
    }
}
