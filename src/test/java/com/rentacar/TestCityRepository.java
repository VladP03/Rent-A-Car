package com.rentacar;

import com.rentacar.model.CityDTO;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.repository.city.City;
import com.rentacar.repository.city.CityRepository;
import com.rentacar.service.CityService;
import com.rentacar.service.exceptions.car.CarAlreadyExistsException;
import com.rentacar.service.exceptions.city.CityAlreadyExistsException;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestCityRepository {

    private CityDTO baseCityDTO;
    private City baseCity;

    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void init() {
        baseCityDTO = new CityDTO()
                .setId(null)
                .setName("TEST");

        baseCity = CityAdapter.fromDTO(baseCityDTO);
    }

    @Test
    void TestCreateCity_ValidParameters() {

        Mockito.when(cityRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(cityRepositoryMock.save(Mockito.any(City.class))).thenReturn(baseCity);

        CityDTO cityCreated = cityService.createCity(baseCityDTO);

        Assertions.assertEquals(baseCityDTO, cityCreated);
    }

    @Test
    void TestCreateCity_CityAlreadyExists() {

        Mockito.when(cityRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(CityAdapter.fromDTO(baseCityDTO)));

        CityAlreadyExistsException exception = assertThrows(
                CityAlreadyExistsException.class,
                () -> cityService.createCity(baseCityDTO)
        );

        assertEquals("City already exists. Error on the following city: " + baseCityDTO.getName(), exception.getMessage());
    }

    @Test
    void TestUpdateCity_ValidParameters() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(CityAdapter.fromDTO(baseCityDTO)));
        Mockito.when(cityRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(cityRepositoryMock.save(Mockito.any(City.class))).thenReturn(CityAdapter.fromDTO(baseCityDTO));

        CityDTO cityUpdated = cityService.updateCity(baseCityDTO);

        Assertions.assertEquals(baseCityDTO, cityUpdated);
    }

    @Test
    void TestUpdateCity_CityDoesNotExists() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(cityRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(
                CityNotFoundException.class,
                () -> cityService.updateCity(baseCityDTO)
        );

        assertEquals("City not found. In database does not exists an city with id " + baseCityDTO.getId() + ".", exception.getMessage());
    }

    @Test
    void TestUpdateCity_CityAlreadyExists() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(CityAdapter.fromDTO(baseCityDTO)));
        Mockito.when(cityRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(CityAdapter.fromDTO(baseCityDTO)));

        CityAlreadyExistsException exception = assertThrows(
                CityAlreadyExistsException.class,
                () -> cityService.updateCity(baseCityDTO)
        );

        assertEquals("City already exists. Error on the following city: " + baseCityDTO.getName(), exception.getMessage());
    }

    @Test
    void TestDeleteCity_ValidParameters() {
        baseCityDTO.setId(1);

        Mockito.when(cityRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(CityAdapter.fromDTO(baseCityDTO)));

        CityDTO cityDeleted = cityService.deleteCity(baseCityDTO.getId());

        Assertions.assertEquals(baseCityDTO, cityDeleted);
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
