package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {
    Optional<City> findByNameIgnoreCaseAndStateIgnoreCaseAndCountryIgnoreCase(
            String name, String state, String country
    );

    boolean existsByNameIgnoreCaseAndStateIgnoreCaseAndCountryIgnoreCase(
            String name, String state, String country
    );
}
