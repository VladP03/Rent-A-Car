package com.rentacar.repository.dealership;

import com.rentacar.repository.car.Car;
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
    private String city;
    private String country;
    private String email;
    private String phoneNumber;

    @OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="DEALER_ID") // join column is in table for Order
    private List<Car> cars;
}
