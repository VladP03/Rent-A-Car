package com.rentacar.repository.car;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByVIN(String VIN);
    Optional<List<Car>> findByIDOrBrandName(Integer id, String brandName);
}
