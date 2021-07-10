package com.rentacar.model;

import com.rentacar.model.validations.OnCreate;
import com.rentacar.model.validations.OnUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@Getter @Setter
public class CityDTO {
    @Null(message = "City's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "City's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "City's ID must be at least 1", groups = {OnUpdate.class})
    private Integer id;

    @NotEmpty(message = "City's name can not be null or empty")
    private String name;


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (! (obj instanceof CityDTO)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        CityDTO cityDTO = (CityDTO) obj;

        if (this.getId() == null && cityDTO.getId() == null) {

        } else if ((this.getId() != null && cityDTO.getId() == null) || (this.getId() == null && cityDTO.getId() != null)) {
            return false;
        } else if (!this.getId().equals(cityDTO.getId())) {
            return false;
        }

        if (!this.getName().equals(cityDTO.getName())) {
            return false;
        }

        return true;
    }
}
