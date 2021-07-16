package com.rentacar.repository.city;

import lombok.*;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "citySeq")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class City {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citySeq")
    private Integer id;

    @Column(unique = true)
    private String name;
}
