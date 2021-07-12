package com.rentacar.repository.country;

import com.rentacar.repository.city.City;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@SequenceGenerator(name = "countrySeq")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countrySeq")
    private Integer id;

    private String name;
    private String phoneNumber;

    @OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="country_id")
    private List<City> cityList;
}
