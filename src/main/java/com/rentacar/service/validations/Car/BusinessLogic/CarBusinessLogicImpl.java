package com.rentacar.service.validations.Car.BusinessLogic;

import com.rentacar.model.CarDTO;
import com.rentacar.service.exceptions.car.CarFirstRegistrationException;
import com.rentacar.service.exceptions.car.CarFuelException;
import com.rentacar.service.exceptions.car.CarGearboxException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CarBusinessLogicImpl implements CarValidateBusinessLogic, CarBusinessLogic {

    private final CarDTO carDTO;



    public CarBusinessLogicImpl(CarDTO carDTO) {
        this.carDTO = carDTO;
    }



    @Override
    public void validateBusinessLogic() {
        validateFirstRegistration();
        validateFuel();
        validateGearBox();
    }



    @Override
    public void validateFirstRegistration() {
        if (carDTO.getFirstRegistration() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new CarFirstRegistrationException(carDTO);
        } else if (carDTO.getFirstRegistration() < Calendar.getInstance().get(Calendar.YEAR) - 10) {
            throw new CarFirstRegistrationException(carDTO);
        }
    }


    @Override
    public void validateFuel() {
        List<String> fuelType = new ArrayList<>();

        fuelType.add("GAS");
        fuelType.add("DIESEL");
        fuelType.add("HYBRID");
        fuelType.add("ELECTRIC");

        if (!fuelType.contains(carDTO.getFuel().toUpperCase())) {
            throw new CarFuelException(carDTO);
        }
    }


    @Override
    public void validateGearBox() {
        List<String> gearBoxType = new ArrayList<>();

        gearBoxType.add("MANUAL");
        gearBoxType.add("AUTOMATIC");

        if (!gearBoxType.contains(carDTO.getGearbox().toUpperCase())) {
            throw new CarGearboxException(carDTO);
        }
    }
}
