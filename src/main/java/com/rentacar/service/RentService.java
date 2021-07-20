package com.rentacar.service;

import com.rentacar.model.CarDTO;
import com.rentacar.model.DealershipDTO;
import com.rentacar.model.RentDTO;
import com.rentacar.model.adapters.CarAdapter;
import com.rentacar.model.adapters.RentAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import com.rentacar.repository.rent.Rent;
import com.rentacar.repository.rent.RentRepository;
import com.rentacar.service.exceptions.car.CarNotFoundException;
import com.rentacar.service.exceptions.dealership.DealershipNotFoundException;
import com.rentacar.service.exceptions.rent.RentCarIndisponibleException;
import com.rentacar.service.exceptions.rent.RentDateInvalidException;
import com.rentacar.service.exceptions.rent.RentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Validated
public class RentService {

    private final RentRepository rentRepository;

    private final DealershipService dealershipService;



    public RentService(RentRepository rentRepository, DealershipService dealershipService) {
        this.rentRepository = rentRepository;
        this.dealershipService = dealershipService;
    }



    public List<RentDTO> getRent() {
        return RentAdapter.toListDTO(rentRepository.findAll());
    }

    @Validated(OnCreate.class)
    public RentDTO createRent(Integer dealershipId, @Valid RentDTO rentDTO) {
        DealershipDTO dealershipFounded = dealershipService.getDealership(dealershipId);

        if (dealershipFounded != null) {
            checkDateStartBeforeEnd(rentDTO.getStartDate(), rentDTO.getEndDate());
            checkDateProperEnd(rentDTO.getEndDate());
            checkIfDealershipHaveCar(dealershipFounded, rentDTO);
            checkIfCarIsDisponible(rentDTO);

            return RentAdapter.toDTO(rentRepository.save(RentAdapter.fromDTO(rentDTO)));

        } else {
            throw new DealershipNotFoundException(dealershipId);
        }
    }

    @Validated(OnUpdate.class)
    public RentDTO updateRent(@Valid RentDTO rentDTO) {
        Optional<Rent> rentFounded = rentRepository.findById(rentDTO.getID());

        if (rentFounded.isPresent()) {
            checkDateStartBeforeEnd(rentDTO.getStartDate(), rentDTO.getEndDate());
            checkDateProperEnd(rentDTO.getEndDate());
            checkIfDealershipHaveCar(RentAdapter.toDTO(rentFounded.get()), rentDTO);
            checkIfCarIsDisponible(rentDTO);

            return RentAdapter.toDTO(rentRepository.save(RentAdapter.fromDTO(rentDTO)));
        } else {
            throw new RentNotFoundException(rentDTO.getID());
        }
    }

    @Validated(OnUpdate.class)
    public RentDTO patchRent(@Valid RentDTO rentDTO) {
        Optional<Rent> rentFounded = rentRepository.findById(rentDTO.getID());

        if (rentFounded.isPresent()) {

            if (rentDTO.getCar() != null) {
                checkIfDealershipHaveCar(RentAdapter.toDTO(rentFounded.get()), rentDTO);
                checkIfCarIsDisponible(rentDTO);

                rentFounded.get().setCar(CarAdapter.fromDTO(rentDTO.getCar()));
                rentRepository.save(rentFounded.get());
            }

            if (rentDTO.getStartDate() != null && rentDTO.getEndDate() != null) {
                checkDateStartBeforeEnd(rentDTO.getStartDate(), rentDTO.getEndDate());
                checkDateProperEnd(rentDTO.getEndDate());

                rentFounded.get().setStartDate(rentDTO.getStartDate());
                rentFounded.get().setEndDate(rentDTO.getEndDate());

                rentRepository.save(rentFounded.get());
            } else {
                if (rentDTO.getStartDate() != null) {
                    checkDateStartBeforeEnd(rentDTO.getStartDate(), rentFounded.get().getEndDate());

                    rentFounded.get().setStartDate(rentDTO.getStartDate());
                }

                if (rentDTO.getEndDate() != null) {
                    checkDateStartBeforeEnd(rentFounded.get().getStartDate(), rentDTO.getEndDate());
                    checkDateProperEnd(rentDTO.getEndDate());

                    rentFounded.get().setEndDate(rentDTO.getEndDate());
                }

                rentRepository.save(rentFounded.get());
            }

            return RentAdapter.toDTO(rentFounded.get());
        } else {
            throw new RentNotFoundException(rentDTO.getID());
        }
    }

    public RentDTO deleteRent(Integer id) {
        Optional<Rent> rentFounded = rentRepository.findById(id);

        if (rentFounded.isPresent()) {
            rentRepository.deleteById(id);

            return RentAdapter.toDTO(rentFounded.get());
        } else {
            throw new RentNotFoundException(id);
        }
    }



    private void checkDateStartBeforeEnd (String startDate, String endDate) {
        try {
            Date start_date = new SimpleDateFormat("dd.MM.yyyy").parse(startDate);
            Date end_date = new SimpleDateFormat("dd.MM.yyyy").parse(endDate);

            if (start_date.after(end_date)) {
                throw new RentDateInvalidException(startDate, endDate);
            }
        } catch (ParseException exception) {
            throw new RuntimeException();
        }
    }

    private void checkDateProperEnd(String endDate) {
        try {
            Date end_date = new SimpleDateFormat("dd.MM.yyyy").parse(endDate);

            if (end_date.before(Calendar.getInstance().getTime())) {
                throw new RentDateInvalidException(endDate);
            }

        } catch (ParseException exception) {
            throw new RuntimeException();
        }
    }

    private void checkIfDealershipHaveCar(DealershipDTO dealershipDTO, RentDTO rentDTO) {
        if (!dealershipDTO.getCars().contains(rentDTO.getCar())) {
            throw new CarNotFoundException(rentDTO);
        }
    }

    private void checkIfDealershipHaveCar(RentDTO rentFounded, RentDTO rentDTO) {
        DealershipDTO dealershipFounded = dealershipService.getDealership(rentFounded.getCar());

        if (!dealershipFounded.getCars().contains(rentDTO.getCar())) {
            throw new CarNotFoundException(rentDTO);
        }
    }

    private void checkIfCarIsDisponible(RentDTO rentDTO) {
        Optional<List<Rent>> rentList = rentRepository.findByCar(CarAdapter.fromDTO(rentDTO.getCar()));

        if (rentList.isPresent()) {
            for (RentDTO rent : RentAdapter.toListDTO(rentList.get())) {
                if (!checkCarIfCanBeRent(rentDTO, rent)) {
                    throw new RentCarIndisponibleException(rentDTO);
                }
            }
        }
    }

    private boolean checkCarIfCanBeRent(RentDTO first, RentDTO second) {
        try {
            Date first_startDate = new SimpleDateFormat("dd.MM.yyyy").parse(first.getStartDate());
            Date first_endDate = new SimpleDateFormat("dd.MM.yyyy").parse(first.getEndDate());

            Date second_startDate = new SimpleDateFormat("dd.MM.yyyy").parse(second.getStartDate());
            Date second_endDate = new SimpleDateFormat("dd.MM.yyyy").parse(second.getEndDate());

            if (first_startDate.before(second_startDate)) {
                if (first_endDate.before(second_endDate)) {
                    return true;
                }
            } else if (first_startDate.after(second_startDate)) {
                if (first_endDate.after(second_endDate)) {
                    return true;
                }
            }

        } catch (ParseException exception) {
            throw new RuntimeException();
        }

        return false;
    }
}
