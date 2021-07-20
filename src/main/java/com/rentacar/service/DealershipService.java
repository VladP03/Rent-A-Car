package com.rentacar.service;

import java.util.*;

import com.rentacar.model.CarDTO;
import com.rentacar.model.CityDTO;
import com.rentacar.model.DealershipDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.model.adapters.CityAdapter;
import com.rentacar.model.adapters.CountryAdapter;
import com.rentacar.model.adapters.DealershipAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.dealership.Dealership;
import com.rentacar.repository.dealership.DealershipRepository;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.EmailUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.PhoneNumberUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.VinUniqueConstraintException;
import com.rentacar.service.exceptions.dealership.DealershipCityInvalidException;
import com.rentacar.service.exceptions.dealership.DealershipNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class DealershipService {

    private final DealershipRepository dealershipRepository;

    private final CarService carService;
    private final CountryService countryService;
    private final CityService cityService;



    public DealershipService(DealershipRepository dealershipRepository, CarService carService, CountryService countryService, CityService cityService) {
        this.dealershipRepository = dealershipRepository;
        this.carService = carService;
        this.countryService = countryService;
        this.cityService = cityService;
    }



    public List<DealershipDTO> getDealership(Integer id, String name) {
        if (id == null && name == null) {
            return DealershipAdapter.toListDTO(dealershipRepository.findAll());
        } else if (id != null && name != null) {
            Optional<Dealership> dealershipFounded = dealershipRepository.findByIDAndName(id, name);

            if (dealershipFounded.isPresent()) {
                return Collections.singletonList(DealershipAdapter.toDTO(dealershipFounded.get()));
            } else {
                throw  new DealershipNotFoundException(id, name);
            }
        } else {
            Optional<Dealership> dealershipFounded = dealershipRepository.findByIDOrName(id, name);

            if (dealershipFounded.isPresent()) {
                return Collections.singletonList(DealershipAdapter.toDTO(dealershipFounded.get()));
            } else if (name == null){
                throw new DealershipNotFoundException(id);
            } else {
                throw new DealershipNotFoundException(name);
            }
        }
    }

    protected DealershipDTO getDealership(Integer id) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findById(id);

        return dealershipFounded.map(DealershipAdapter::toDTO).orElse(null);
    }

    protected DealershipDTO getDealership(CarDTO carDTO) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findByCarsIn(Collections.singletonList(CarAdapter.fromDTO(carDTO)));

        return dealershipFounded.map(DealershipAdapter::toDTO).orElse(null);
    }

    @Validated(OnCreate.class)
    public DealershipDTO createDealership(@Valid DealershipDTO dealershipDTO) {
        if (countryService.getCountry(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName()) != null) {
            if (cityService.getCity(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName()) != null) {
                namesToUpper(dealershipDTO);

                if (!isCityInCountryCitiesList(dealershipDTO)) {
                    throw new DealershipCityInvalidException(dealershipDTO);
                }

                if (dealershipDTO.getCars() != null) {
                    createCar(dealershipDTO.getCars());
                } else {
                    dealershipDTO.setCars(Collections.emptyList());
                }

                dealershipDTO.setPhoneNumber(rewritePhoneNumber(dealershipDTO.getPhoneNumber(), dealershipDTO.getCountry().getPhoneNumber()));

                try {
                    return DealershipAdapter.toDTO(dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO)));
                } catch (DataIntegrityViolationException exception) {
                    if (!isUniqueEmail(dealershipDTO.getEmail())) {
                        throw new EmailUniqueConstraintException(dealershipDTO);
                    }

                    if (!isUniquePhoneNumber(dealershipDTO.getPhoneNumber())) {
                        throw new PhoneNumberUniqueConstraintException(dealershipDTO);
                    }

                    throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
                }
            } else {
                throw new CityNotFoundException(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName());
            }
        } else {
            throw new CountryNotFoundException(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName());
        }
    }

    @Validated(OnUpdate.class)
    public DealershipDTO updateDealership(@Valid DealershipDTO dealershipDTO) {
        if (existsDealership(dealershipDTO.getID())) {
            if (countryService.getCountry(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName()) != null) {
                if (cityService.getCity(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName()) != null) {
                    namesToUpper(dealershipDTO);

                    if (!isCityInCountryCitiesList(dealershipDTO)) {
                        throw new DealershipCityInvalidException(dealershipDTO);
                    }

                    if (dealershipDTO.getCars() != null) {
                        createCar(dealershipDTO.getCars());
                    } else {
                        dealershipDTO.setCars(Collections.emptyList());
                    }

                    dealershipDTO.setPhoneNumber(rewritePhoneNumber(dealershipDTO.getPhoneNumber(), dealershipDTO.getCountry().getPhoneNumber()));

                    try {
                        return DealershipAdapter.toDTO(dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO)));
                    } catch (DataIntegrityViolationException exception) {
                        if (!isUniqueEmail(dealershipDTO.getEmail())) {
                            throw new EmailUniqueConstraintException(dealershipDTO);
                        }

                        if (!isUniquePhoneNumber(dealershipDTO.getPhoneNumber())) {
                            throw new PhoneNumberUniqueConstraintException(dealershipDTO);
                        }

                        throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
                    }
                } else {
                    throw new CityNotFoundException(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName());
                }
            } else {
                throw new CountryNotFoundException(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName());
            }
        } else {
            throw new DealershipNotFoundException(dealershipDTO.getID());
        }
    }

    @Validated(OnUpdate.class)
    public DealershipDTO patchDealership(@Valid DealershipDTO dealershipDTO) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findById(dealershipDTO.getID());

        if (dealershipFounded.isPresent()) {

            if (dealershipDTO.getName() != null) {
                dealershipFounded.get().setName(dealershipDTO.getName().toUpperCase());

                dealershipRepository.save(dealershipFounded.get());
            }

            if (dealershipDTO.getCountry() != null) {
                if (countryService.getCountry(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName()) != null) {
                    dealershipFounded.get().setCountry(CountryAdapter.fromDTO(dealershipDTO.getCountry()));

                    dealershipRepository.save(dealershipFounded.get());
                } else {
                    throw new CountryNotFoundException(dealershipDTO.getCountry().getId(), dealershipDTO.getCountry().getName());
                }
            }

            if (dealershipDTO.getCity() != null) {
                if (cityService.getCity(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName()) != null) {
                    if (!isCityInCountryCitiesList(dealershipDTO)) {
                        throw new DealershipCityInvalidException(dealershipDTO);
                    }

                    dealershipFounded.get().setCity(CityAdapter.fromDTO(dealershipDTO.getCity()));

                    dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO));
                } else {
                    throw new CityNotFoundException(dealershipDTO.getCity().getId(), dealershipDTO.getCity().getName());
                }
            }

            if (dealershipDTO.getEmail() != null) {
                dealershipFounded.get().setEmail(dealershipDTO.getEmail());

                try {
                    dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO));
                } catch (DataIntegrityViolationException exception) {
                    if (!isUniqueEmail(dealershipDTO.getEmail())) {
                        throw new EmailUniqueConstraintException(dealershipDTO);
                    }

                    throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
                }
            }

            if (dealershipDTO.getPhoneNumber() != null) {
                dealershipFounded.get().setPhoneNumber(rewritePhoneNumber(dealershipDTO.getPhoneNumber(), dealershipDTO.getCountry().getPhoneNumber()));

                try {
                    dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO));
                } catch (DataIntegrityViolationException exception) {
                    if (!isUniquePhoneNumber(dealershipDTO.getEmail())) {
                        throw new PhoneNumberUniqueConstraintException(dealershipDTO);
                    }

                    throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
                }
            }

            if (dealershipDTO.getCars() != null) {
                createCar(dealershipDTO.getCars());

                dealershipFounded.get().getCars().clear();
                dealershipFounded.get().getCars().addAll(CarAdapter.fromListDTO(dealershipDTO.getCars()));

                try {
                    dealershipRepository.save(DealershipAdapter.fromDTO(dealershipDTO));
                } catch (DataIntegrityViolationException exception) {
                    for (CarDTO carDTO : dealershipDTO.getCars()) {
                        if (!carService.isUniqueVIN(carDTO.getVIN())) {
                            throw new VinUniqueConstraintException(carDTO);
                        }
                    }

                    throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
                }
            }

            return DealershipAdapter.toDTO(dealershipFounded.get());
        } else {
            throw new DealershipNotFoundException(dealershipDTO.getID());
        }
    }

    public DealershipDTO patchDealershipAddCars(Integer id, List<CarDTO> carList) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findById(id);

        if (dealershipFounded.isPresent()) {
            if (carList != null) {
                createCar(carList);

                dealershipFounded.get().getCars().addAll(CarAdapter.fromListDTO(carList));

                try {
                    dealershipRepository.save(dealershipFounded.get());
                } catch (DataIntegrityViolationException exception) {
                    for (CarDTO carDTO : carList) {
                        if (!carService.isUniqueVIN(carDTO.getVIN())) {
                            throw new VinUniqueConstraintException(carDTO);
                        }
                    }

                    throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
                }
            }

            return DealershipAdapter.toDTO(dealershipFounded.get());
        } else {
            throw new DealershipNotFoundException(id);
        }
    }

    public DealershipDTO deleteDealership(Integer id) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findById(id);

        if (dealershipFounded.isPresent()) {
            dealershipRepository.deleteById(id);

            return DealershipAdapter.toDTO(dealershipFounded.get());
        } else {
            throw new DealershipNotFoundException(id);
        }
    }



    private boolean isUniqueEmail(String email) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findByEmail(email);

        return !dealershipFounded.isPresent();
    }

    private boolean isUniquePhoneNumber(String phoneNumber) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findByPhoneNumber(phoneNumber);

        return !dealershipFounded.isPresent();
    }

    private boolean isCityInCountryCitiesList(DealershipDTO dealershipDTO) {

        for (CityDTO cityDTO : dealershipDTO.getCountry().getCityList()) {
            if (cityDTO.equals(dealershipDTO.getCity())) {
                return true;
            }
        }

        return false;
    }

    private boolean existsDealership(Integer id) {
        Optional<Dealership> dealershipFounded = dealershipRepository.findById(id);

        return dealershipFounded.isPresent();
    }

    private String rewritePhoneNumber (String dealerNumber, String countryNumber) {
        String dealerNumberVisible = dealerNumber.substring(0,3) + "-" + dealerNumber.substring(3, 7) + "-" + dealerNumber.substring(7);

        return countryNumber + " " + dealerNumberVisible;
    }

    private void createCar(List<CarDTO> carDTOList) {
        for (CarDTO carDTO : carDTOList) {
            carService.createCar(carDTO);
        }
    }


    private void namesToUpper(DealershipDTO dealershipDTO) {
        dealershipDTO.setName(dealershipDTO.getName().toUpperCase());
        dealershipDTO.setEmail(dealershipDTO.getEmail().toUpperCase());
        dealershipDTO.getCity().setName(dealershipDTO.getCity().getName().toUpperCase());
    }
}
