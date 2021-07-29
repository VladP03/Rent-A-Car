package com.rentacar;

import com.rentacar.model.CountryDTO;
import com.rentacar.model.adapters.CountryAdapter;
import com.rentacar.repository.country.CountryRepository;
import com.rentacar.service.CountryService;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
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
public class TestCountry {

    @Mock
    private CountryRepository countryRepositoryMock;
    @InjectMocks
    private CountryService countryService;

    private CountryDTO countryDTO;

    @BeforeEach
    public void init () {
        countryDTO = new CountryDTO();

        countryDTO.setId(null);
        countryDTO.setName("TestName");
        countryDTO.setPhoneNumber("testNumber");
        countryDTO.setCityList(Collections.emptyList());

        namesToUpper();
    }

    public void namesToUpper() {
        countryDTO.setName(countryDTO.getName().toUpperCase());
    }


    /* getCountry() */

    @Test
    public void testCountry_getCountry_idAndNameNull() {
        Mockito.when(countryRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<CountryDTO> countryDTOList = countryService.getCountry(null, null);

        Assertions.assertEquals(Collections.emptyList(), countryDTOList);
    }

    @Test
    public void testCountry_getCountry_idNull() {
        Mockito.when(countryRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.of(CountryAdapter.fromDTO(countryDTO)));

        List<CountryDTO> countryDTOList = countryService.getCountry(null, Mockito.anyString());

        Assertions.assertEquals(Collections.singletonList(countryDTO), countryDTOList);
    }

    @Test
    public void testCountry_getCountry_idNull_CountryNotFound() {
        Mockito.when(countryRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        CountryNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CountryNotFoundException.class,
                () -> countryService.getCountry(null, countryDTO.getName()));

        Assertions.assertEquals("Country not found. In database does not exists an country with name " + countryDTO.getName() + ".", exception.getMessage());
    }

    @Test
    public void testCountry_getCountry_nameNull() {
        countryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CountryAdapter.fromDTO(countryDTO)));

        List<CountryDTO> countryDTOList = countryService.getCountry(countryDTO.getId(), null);

        Assertions.assertEquals(Collections.singletonList(countryDTO), countryDTOList);
    }

    @Test
    public void testCountry_getCountry_nameNull_CityNotFound() {
        countryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CountryNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CountryNotFoundException.class,
                () -> countryService.getCountry(countryDTO.getId(), null));

        Assertions.assertEquals("Country not found. In database does not exists an country with id " + countryDTO.getId() + ".", exception.getMessage());
    }

    @Test
    public void testCountry_getCountry_IdAndNameNotNull() {
        countryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findByIdAndName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(CountryAdapter.fromDTO(countryDTO)));

        List<CountryDTO> countryDTOList = countryService.getCountry(countryDTO.getId(), countryDTO.getName());

        Assertions.assertEquals(Collections.singletonList(countryDTO), countryDTOList);
    }

    @Test
    public void testCountry_getCountry_IdAndNameNotNull_CityNotFound() {
        countryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findByIdAndName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.empty());

        CountryNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CountryNotFoundException.class,
                () -> countryService.getCountry(countryDTO.getId(), countryDTO.getName()));

        Assertions.assertEquals("Country not found. In database does not exists an country with id " + countryDTO.getId() + " and name " + countryDTO.getName() + ".", exception.getMessage());
    }


    /* updateCountry() */

    @Test
    public void testCountry_updateCountry_CountryNotFound() {
        countryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CountryNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CountryNotFoundException.class,
                () -> countryService.updateCountry(countryDTO));

        Assertions.assertEquals("Country not found. In database does not exists an country with id " + countryDTO.getId() + ".", exception.getMessage());
    }


    /* patchCountry() */

    @Test
    public void testCountry_patchCountry_CountryNotFound() {
        countryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CountryNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CountryNotFoundException.class,
                () -> countryService.patchCountry(countryDTO));

        Assertions.assertEquals("Country not found. In database does not exists an country with id " + countryDTO.getId() + ".", exception.getMessage());
    }


    /* deleteCountry() */

    @Test
    public void testCountry_deleteCountry_CountryNotFound() {
        countryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CountryNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                CountryNotFoundException.class,
                () -> countryService.deleteCountry(countryDTO.getId()));

        Assertions.assertEquals("Country not found. In database does not exists an country with id " + countryDTO.getId() + ".", exception.getMessage());
    }
}
