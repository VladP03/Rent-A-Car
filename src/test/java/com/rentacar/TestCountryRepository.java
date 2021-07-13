package com.rentacar;

import java.util.*;

import com.rentacar.model.CityDTO;
import com.rentacar.model.CountryDTO;
import com.rentacar.model.adapters.CountryAdapter;
import com.rentacar.repository.country.Country;
import com.rentacar.repository.country.CountryRepository;
import com.rentacar.service.CityService;
import com.rentacar.service.CountryService;
import com.rentacar.service.exceptions.country.CountryAlreadyExistsException;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestCountryRepository {

    CountryDTO baseCountryDTO;

    @Mock
    CountryRepository countryRepositoryMock;
    @InjectMocks
    CountryService countryService;

    @Mock
    CityService cityService;

    @BeforeEach
    void init() {
        baseCountryDTO = CountryDTO.builder()
                .id(null)
                .name("TestCountryName")
                .phoneNumber("TestCountryPhoneNumber")
                .cityList(
                        Collections.singletonList( CityDTO.builder()
                                .id(null)
                                .name("TestCityName")
                                .build()))
                .build();

        nameToUpper();
    }

    void nameToUpper() {
        baseCountryDTO.setName(baseCountryDTO.getName().toUpperCase());

        for (CityDTO cityDTO : baseCountryDTO.getCityList()) {
            cityDTO.setName(cityDTO.getName().toUpperCase());
        }
    }

    @Test
    void TestGetCountry_IdAndNameNull() {
        baseCountryDTO.setId(null);
        baseCountryDTO.setName(null);

        Mockito.when(countryRepositoryMock.findAll()).thenReturn(Collections.singletonList(CountryAdapter.fromDTO(baseCountryDTO)));

        List<CountryDTO> countryDTOList = countryService.getCountry(baseCountryDTO.getId(), baseCountryDTO.getName());

        assertEquals(Collections.singletonList(baseCountryDTO), countryDTOList);
    }

    @Test
    void TestGetCountry_IdAndNameNotNull() {
        baseCountryDTO.setId(1);
        baseCountryDTO.setName("TestCountryName");

        nameToUpper();

        Mockito.when(countryRepositoryMock.findByIdAndName(baseCountryDTO.getId(), baseCountryDTO.getName())).thenReturn(Optional.of(Collections.singletonList(CountryAdapter.fromDTO(baseCountryDTO))));

        List<CountryDTO> countryDTOList = countryService.getCountry(baseCountryDTO.getId(), baseCountryDTO.getName());

        assertEquals(Collections.singletonList(baseCountryDTO), countryDTOList);
    }

    @Test
    void TestGetCountry_IdAndNameNotNull_CountryNotFoundException() {
        baseCountryDTO.setId(1);
        baseCountryDTO.setName("TestCountryName");

        nameToUpper();

        Mockito.when(countryRepositoryMock.findByIdAndName(baseCountryDTO.getId(), baseCountryDTO.getName())).thenReturn(Optional.empty());

        CountryNotFoundException exception = assertThrows(
                CountryNotFoundException.class,
                () -> countryService.getCountry(baseCountryDTO.getId(), baseCountryDTO.getName())
        );

        assertEquals("Country not found. In database does not exists an country with id " + baseCountryDTO.getId() + " and name " + baseCountryDTO.getName() + ".", exception.getMessage());
    }

    @Test
    void TestGetCountry_IdNull() {
        baseCountryDTO.setId(null);
        baseCountryDTO.setName("TestCountryName");

        nameToUpper();

        Mockito.when(countryRepositoryMock.findByIdOrName(baseCountryDTO.getId(), baseCountryDTO.getName())).thenReturn(Optional.of(Collections.singletonList(CountryAdapter.fromDTO(baseCountryDTO))));

        List<CountryDTO> countryFounded = countryService.getCountry(baseCountryDTO.getId(), baseCountryDTO.getName());

        assertEquals(Collections.singletonList(baseCountryDTO), countryFounded);
    }

    @Test
    void TestGetCountry_IdNull_CountryNotFoundException() {
        baseCountryDTO.setId(null);
        baseCountryDTO.setName("TestCountryName");

        nameToUpper();

        Mockito.when(countryRepositoryMock.findByIdOrName(baseCountryDTO.getId(), baseCountryDTO.getName())).thenReturn(Optional.empty());

        CountryNotFoundException exception = assertThrows(
                CountryNotFoundException.class,
                () -> countryService.getCountry(baseCountryDTO.getId(), baseCountryDTO.getName())
        );

        assertEquals("Country not found. In database does not exists an country with name " + baseCountryDTO.getName() + ".", exception.getMessage());
    }

    @Test
    void TestGetCountry_NameNull() {
        baseCountryDTO.setId(1);
        baseCountryDTO.setName(null);

        Mockito.when(countryRepositoryMock.findByIdOrName(baseCountryDTO.getId(), baseCountryDTO.getName())).thenReturn(Optional.of(Collections.singletonList(CountryAdapter.fromDTO(baseCountryDTO))));

        List<CountryDTO> countryFounded = countryService.getCountry(baseCountryDTO.getId(), baseCountryDTO.getName());

        assertEquals(Collections.singletonList(baseCountryDTO), countryFounded);
    }

    @Test
    void TestGetCountry_NameNull_CountryNotFoundException() {
        baseCountryDTO.setId(1);
        baseCountryDTO.setName(null);

        Mockito.when(countryRepositoryMock.findByIdOrName(baseCountryDTO.getId(), baseCountryDTO.getName())).thenReturn(Optional.empty());

        CountryNotFoundException exception = assertThrows(
                CountryNotFoundException.class,
                () -> countryService.getCountry(baseCountryDTO.getId(), baseCountryDTO.getName())
        );

        assertEquals("Country not found. In database does not exists an country with id " + baseCountryDTO.getId() + ".", exception.getMessage());
    }

    @Test
    void TestCreateCountry_ValidParameters(){

        Mockito.when(countryRepositoryMock.findByName(baseCountryDTO.getName())).thenReturn(Optional.empty());
        Mockito.when(countryRepositoryMock.findByPhoneNumber(baseCountryDTO.getPhoneNumber())).thenReturn(Optional.empty());
        Mockito.when(countryRepositoryMock.save(Mockito.any(Country.class))).thenReturn(CountryAdapter.fromDTO(baseCountryDTO));
        Mockito.doNothing().when(cityService).createCity(Mockito.any(CityDTO.class));

        CountryDTO countryDTOCreated = countryService.createCountry(baseCountryDTO);

        assertEquals(baseCountryDTO, countryDTOCreated);
    }

    @Test
    void TestCreateCountry_InvalidNameAndPhoneNumber(){

        Mockito.when(countryRepositoryMock.findByName(baseCountryDTO.getName())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));
        Mockito.when(countryRepositoryMock.findByPhoneNumber(baseCountryDTO.getPhoneNumber())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));

        CountryAlreadyExistsException exception = assertThrows(
                CountryAlreadyExistsException.class,
                () -> countryService.createCountry(baseCountryDTO)
        );

        assertEquals("Country already exists. Error on the following country: " + baseCountryDTO.getName(), exception.getMessage());
        assertEquals("Be careful at these parameters: name, phone number.", exception.getDebugMessage());
    }

    @Test
    void TestCreateCountry_PhoneExists(){

        Mockito.when(countryRepositoryMock.findByName(baseCountryDTO.getName())).thenReturn(Optional.empty());
        Mockito.when(countryRepositoryMock.findByPhoneNumber(baseCountryDTO.getPhoneNumber())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));

        CountryAlreadyExistsException exception = assertThrows(
                CountryAlreadyExistsException.class,
                () -> countryService.createCountry(baseCountryDTO)
        );

        assertEquals("Country already exists. Error on the following country: " + baseCountryDTO.getName(), exception.getMessage());
        assertEquals("Be careful at these parameter: phone number", exception.getDebugMessage());
    }

    @Test
    void TestCreateCountry_NameExists(){

        Mockito.when(countryRepositoryMock.findByName(baseCountryDTO.getName())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));
        Mockito.when(countryRepositoryMock.findByPhoneNumber(baseCountryDTO.getPhoneNumber())).thenReturn(Optional.empty());

        CountryAlreadyExistsException exception = assertThrows(
                CountryAlreadyExistsException.class,
                () -> countryService.createCountry(baseCountryDTO)
        );

        assertEquals("Country already exists. Error on the following country: " + baseCountryDTO.getName(), exception.getMessage());
        assertEquals("Be careful at these parameter: name", exception.getDebugMessage());
    }
    @Test
    void TestUpdateCountry_ValidParameters(){

        Mockito.when(countryRepositoryMock.findById(baseCountryDTO.getId())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));
        Mockito.when(countryRepositoryMock.findByName(baseCountryDTO.getName())).thenReturn(Optional.empty());
        Mockito.when(countryRepositoryMock.save(Mockito.any(Country.class))).thenReturn(CountryAdapter.fromDTO(baseCountryDTO));
        Mockito.doNothing().when(cityService).createCity(Mockito.any(CityDTO.class));

        CountryDTO countryDTOUpdated = countryService.updateCountry(baseCountryDTO);

        assertEquals(baseCountryDTO, countryDTOUpdated);
    }

    @Test
    void TestUpdateCountry_IdInvalid(){

        Mockito.when(countryRepositoryMock.findById(baseCountryDTO.getId())).thenReturn(Optional.empty());
        Mockito.when(countryRepositoryMock.findByName(baseCountryDTO.getName())).thenReturn(Optional.empty());

        CountryNotFoundException exception = assertThrows(
                CountryNotFoundException.class,
                () -> countryService.updateCountry(baseCountryDTO)
        );

        assertEquals("Country not found. In database does not exists an country with id " + baseCountryDTO.getId() + ".", exception.getMessage());
    }

    @Test
    void TestUpdateCountry_NameInvalid(){

        Mockito.when(countryRepositoryMock.findById(baseCountryDTO.getId())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));
        Mockito.when(countryRepositoryMock.findByName(baseCountryDTO.getName())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));

        CountryAlreadyExistsException exception = assertThrows(
                CountryAlreadyExistsException.class,
                () -> countryService.updateCountry(baseCountryDTO)
        );

        assertEquals("Country already exists. Error on the following country: " + baseCountryDTO.getName(), exception.getMessage());
    }

    // patch

    //

    @Test
    void TestDeleteCountry_ValidParameters(){
        baseCountryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(CountryAdapter.fromDTO(baseCountryDTO)));

        CountryDTO countryDeleted = countryService.deleteCountry(baseCountryDTO.getId());

        assertEquals(baseCountryDTO, countryDeleted);
    }

    @Test
    void TestDeleteCountry_IdInvalid(){
        baseCountryDTO.setId(1);

        Mockito.when(countryRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CountryNotFoundException exception = assertThrows(
                CountryNotFoundException.class,
                () -> countryService.deleteCountry(baseCountryDTO.getId())
        );

        assertEquals("Country not found. In database does not exists an country with id " + baseCountryDTO.getId() + ".", exception.getMessage());
    }
}
