package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Builder
@Getter @Setter
public class CarDTO {
    @Null(message = "Car's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "Car's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "Car's ID must be at least 1", groups = {OnUpdate.class})
    private Integer ID;

    @NotEmpty(message = "Car's brand name may not be null or empty")
    private String brandName;

    @NotEmpty(message = "Car's name may not be null or empty")
    private String name;

    @NotEmpty(message = "Car's VIN may not be null or empty")
    private String VIN;

    @NotNull(message = "Car's first registration may not be null")
    @Positive(message = "Car's first registration may not be negative or zero")
    private Integer firstRegistration;

    @NotNull(message = "Car's engine capacity may not be null")
    @Min(value = 0, message = "Car's engine may not be negative")
    @Max(value = 9999, message = "Car's engine may not be greater than 9999")
    private Integer engineCapacity;

    @NotEmpty(message = "Car's fuel may not be null or empty")
    private String fuel;

    @NotNull(message = "Car's km may not be null")
    @Positive(message = "Car's km can not be negative")
    private Double mileage;

    @NotEmpty(message = "Car's gearbox may not be null or empty")
    private String gearbox;


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (! (obj instanceof CarDTO)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        CarDTO carDTO = (CarDTO) obj;

        if (this.getID() == null && carDTO.getID() == null) {

        } else if ((this.getID() != null && carDTO.getID() == null) || (this.getID() == null && carDTO.getID() != null)) {
            return false;
        } else if (!this.getID().equals(carDTO.getID())) {
            return false;
        }

        if (!this.getBrandName().equals(carDTO.getBrandName())) {
            return false;
        }

        if (!this.getName().equals(carDTO.getName())) {
            return false;
        }

        if (!this.getVIN().equals(carDTO.getVIN())) {
            return false;
        }

        if (!this.getFirstRegistration().equals(carDTO.getFirstRegistration())) {
            return false;
        }

        if (!this.getEngineCapacity().equals(carDTO.getEngineCapacity())) {
            return false;
        }

        if (!this.getFuel().equals(carDTO.getFuel())) {
            return false;
        }

        if (!this.getMileage().equals(carDTO.getMileage())) {
            return false;
        }

        if (!this.getGearbox().equals(carDTO.getGearbox())) {
            return false;
        }

        return true;
    }
}
