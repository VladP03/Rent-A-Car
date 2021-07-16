package com.rentacar.repository.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByName(String name);
    Optional<City> findByIdAndName(Integer id, String name);
    Optional<City> findByIdOrName(Integer id, String name);
}
