package com.rentacar.repository.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByIdAndName(Integer id, String name);
    Optional<Country> findByPhoneNumber(String phoneNumber);
    Optional<Country> findByName(String name);
}
