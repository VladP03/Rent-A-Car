package com.rentacar.repository.country;

import com.rentacar.repository.city.City;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @Column(unique = true)
    private String name;

    @Pattern(regexp = "[+]\\d{2}")
    @Column(length = 3, unique = true)
    private String phoneNumber;

    @OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="country_id")
    private List<City> cityList;
}
