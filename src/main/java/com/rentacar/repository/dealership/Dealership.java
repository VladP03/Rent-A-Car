package com.rentacar.repository.dealership;

import com.rentacar.repository.car.Car;
import com.rentacar.repository.city.City;
import com.rentacar.repository.country.Country;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@SequenceGenerator(name = "DealershipSeq", allocationSize = 1500)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Dealership {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DealershipSeq")
    private Integer ID;

    private String name;

//    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name="CITY_ID")
    private City city;

    @OneToOne
    @JoinColumn(name="COUNTRY_ID")
    private Country country;

    @OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="DEALER_ID") // join column is in table for Order
    private List<Car> cars;
}
