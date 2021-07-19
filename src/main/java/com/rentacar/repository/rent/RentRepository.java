package com.rentacar.repository.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
}
