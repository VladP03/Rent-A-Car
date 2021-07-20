package com.rentacar.repository.dealership;

import com.rentacar.repository.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DealershipRepository extends JpaRepository<Dealership, Integer> {
    Optional<Dealership> findByIDAndName(Integer id, String name);
    Optional<Dealership> findByIDOrName(Integer id, String name);
    Optional<Dealership> findByEmail(String email);
    Optional<Dealership> findByPhoneNumber(String phoneNumber);
    Optional<Dealership> findByCarsIn(List<Car> cars);
}
