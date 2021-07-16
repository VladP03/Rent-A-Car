package com.rentacar;

import com.rentacar.model.CityDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.repository.city.City;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestCityRepository {

    private CityDTO baseCityDTO;

    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void init() {
        baseCityDTO = CityDTO.builder()
                .id(null)
                .name("TestCityName")
                .build();

        baseCityDTO.setName(baseCityDTO.getName().toUpperCase());
    }

    @Test
    void TestGetCity_ValidParameters() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findByIdAndName(baseCityDTO.getId(), baseCityDTO.getName())).thenReturn(Optional.of(CityAdapter.fromDTO(baseCityDTO)));

        List<CityDTO> cityListFounded = cityService.getCity(baseCityDTO.getId(), baseCityDTO.getName());

        Assertions.assertEquals(CityAdapter.toListDTO(Collections.singletonList(CityAdapter.fromDTO(baseCityDTO))), cityListFounded);
    }

    @Test
    void TestGetCity_IdNull() {

        Mockito.when(cityRepositoryMock.findByIdOrName(baseCityDTO.getId(), baseCityDTO.getName())).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(
                CityNotFoundException.class,
                () -> cityService.getCity(baseCityDTO.getId(), baseCityDTO.getName())
        );

        assertEquals("City not found. In database does not exists an city with name " + baseCityDTO.getName() + ".", exception.getMessage());
    }
    @Test
    void TestGetCity_NameNull() {
        baseCityDTO.setId(1);
        baseCityDTO.setName(null);

        Mockito.when(cityRepositoryMock.findByIdOrName(baseCityDTO.getId(), baseCityDTO.getName())).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(
                CityNotFoundException.class,
                () -> cityService.getCity(baseCityDTO.getId(), baseCityDTO.getName())
        );

        assertEquals("City not found. In database does not exists an city with id " + baseCityDTO.getId() + ".", exception.getMessage());
    }

    @Test
    void TestGetCity_IdAndNameNull() {
        baseCityDTO.setName(null);

        CityDTO goodCityDTO = CityDTO.builder()
                .id(1)
                .name("TEST")
                .build();

        Mockito.when(cityRepositoryMock.findAll()).thenReturn(Collections.singletonList(CityAdapter.fromDTO(goodCityDTO)));

        List<CityDTO> cityListFounded = cityService.getCity(baseCityDTO.getId(), baseCityDTO.getName());

        assertEquals(Collections.singletonList(goodCityDTO), cityListFounded);
    }

    @Test
    void TestCreateCityAdmin_ValidParameters() {

        Mockito.when(cityRepositoryMock.save(Mockito.any(City.class))).thenReturn(CityAdapter.fromDTO(baseCityDTO));

        CityDTO cityCreated = cityService.createCityAdmin(baseCityDTO);

        Assertions.assertEquals(baseCityDTO, cityCreated);
    }

    @Test
    void TestUpdateCityAdmin_ValidParameters() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(CityAdapter.fromDTO(baseCityDTO)));
        Mockito.when(cityRepositoryMock.save(Mockito.any(City.class))).thenReturn(CityAdapter.fromDTO(baseCityDTO));

        CityDTO cityUpdated = cityService.updateCityAdmin(baseCityDTO);

        Assertions.assertEquals(baseCityDTO, cityUpdated);
    }

    @Test
    void TestUpdateCityAdmin_CityDoesNotExists() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(
                CityNotFoundException.class,
                () -> cityService.updateCityAdmin(baseCityDTO)
        );

        assertEquals("City not found. In database does not exists an city with id " + baseCityDTO.getId() + ".", exception.getMessage());
    }

    @Test
    void TestDeleteCityAdmin_ValidParameters() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(CityAdapter.fromDTO(baseCityDTO)));

        CityDTO cityDeleted = cityService.deleteCityAdmin(baseCityDTO.getId());

        Assertions.assertEquals(baseCityDTO, cityDeleted);
    }

    @Test
    void TestDeleteCityAdmin_CityNotFound() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(
                CityNotFoundException.class,
                () -> cityService.deleteCityAdmin(baseCityDTO.getId())
        );

        assertEquals("City not found. In database does not exists an city with id " + baseCityDTO.getId() + ".", exception.getMessage());
    }

    @Test
    void TestUpdateCity_CityDoesNotExists() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(
                CityNotFoundException.class,
                () -> cityService.updateCity(baseCityDTO)
        );

        assertEquals("City not found. In database does not exists an city with id " + baseCityDTO.getId() + ".", exception.getMessage());
    }

    @Test
    void TestDeleteCity_CityNotFound() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(
                CityNotFoundException.class,
                () -> cityService.deleteCity(baseCityDTO.getId())
        );

        assertEquals("City not found. In database does not exists an city with id " + baseCityDTO.getId() + ".", exception.getMessage());
    }

}
