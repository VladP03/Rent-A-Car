package com.rentacar.repository.city;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "citySeq")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citySeq")
    private Integer id;

    private String name;

    public City() {

    }

    public Integer getId() {
        return id;
    }

    public City setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }
}
