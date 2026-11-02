package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
