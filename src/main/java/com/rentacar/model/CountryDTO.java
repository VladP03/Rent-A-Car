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
import java.util.List;

@Builder
@Getter @Setter
public class CountryDTO {
    @Null(message = "Country's ID must be null", groups = {OnCreate.class})
    @NotNull(message = "Country's ID must be not null", groups = {OnUpdate.class})
    @Min(value = 1, message = "Country's ID must be at least 1", groups = {OnUpdate.class})
    private Integer id;

    @NotEmpty(message = "Country's name can not be null or empty")
    private String name;

    @NotEmpty(message = "Country's phone number can not be null or empty")
    private String phoneNumber;

    private List<CityDTO> cityList;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (! (obj instanceof CountryDTO)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        CountryDTO countryDTO = (CountryDTO) obj;

        if (this.getId() == null && countryDTO.getId() == null) {

        } else if ((this.getId() != null && countryDTO.getId() == null) || (this.getId() == null && countryDTO.getId() != null)) {
            return false;
        } else if (!this.getId().equals(countryDTO.getId())) {
            return false;
        }

        if (this.getName() == null && countryDTO.getName() == null) {

        } else if ((this.getName() != null && countryDTO.getName() == null) || (this.getName() == null && countryDTO.getName() != null)) {
            return false;
        } else if (!this.getName().equals(countryDTO.getName())) {
            return false;
        }

        if (!this.getPhoneNumber().equals(countryDTO.getPhoneNumber())) {
            return false;
        }

        if (!this.getCityList().containsAll(countryDTO.getCityList()) && !countryDTO.getCityList().containsAll(this.getCityList())) {
            return false;
        }

        return true;
    }
}
