package com.rentacar.repository.car;

import lombok.*;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "CarSeq", allocationSize = 1500)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CarSeq")
    private Integer ID;

    @Column(unique = true)
    private String VIN;

    private String brandName;
    private String name;
    private Integer firstRegistration;
    private Integer engineCapacity;
    private String fuel;
    private Double mileage;
    private String gearbox;
}
