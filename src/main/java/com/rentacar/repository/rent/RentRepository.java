package com.rentacar.repository.rent;

import com.rentacar.repository.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    Optional<List<Rent>> findByCar(Car car);
}
