package com.rentacar.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    boolean existsByVIN(String VIN);
    boolean existsByVINAndID(String VIN, Integer id);

    Optional<List<Car>> findByBrandName(String brandName);
    Optional<Car> findByIDAndBrandName(Integer id, String brandName);
}
