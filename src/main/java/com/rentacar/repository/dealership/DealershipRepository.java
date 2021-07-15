package com.rentacar.repository.dealership;

import com.rentacar.repository.city.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DealershipRepository extends JpaRepository<Dealership, Integer> {
    Optional<Dealership> findByIDAndName(Integer id, String name);
    Optional<Dealership> findByIDOrName(Integer id, String name);
    Optional<Dealership> findByNameAndCity(String name, City city);
    Optional<Dealership> findByName(String name);
    Optional<Dealership> findByEmail(String email);
}
