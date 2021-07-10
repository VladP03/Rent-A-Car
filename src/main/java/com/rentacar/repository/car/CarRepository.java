package com.rentacar.repository.car;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<List<Car>> findAllByBrandName(String brandName);
    Optional<Car> findByVIN(String VIN);
}
