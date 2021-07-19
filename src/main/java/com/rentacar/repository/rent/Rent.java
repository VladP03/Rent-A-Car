package com.rentacar.repository.rent;

import com.rentacar.repository.car.Car;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "RentSeq", allocationSize = 1500)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rent {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RentSeq")
    private Integer ID;

    @ManyToOne
    @JoinColumn(name = "CAR_ID")
    private Car car;

    private String startDate;
    private String endDate;
}
