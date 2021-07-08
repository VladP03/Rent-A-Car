package com.rentacar.repository.dealership;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealershipRepository extends JpaRepository<Dealership, Integer> {
    Dealership findByNameAndCountryAndCityAndEmailAndPhoneNumber(String name, String country, String city, String email, String phoneNumber);
}
