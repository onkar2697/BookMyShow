package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Screen;
import org.example.bookmyshow.theatreservice.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {

    Optional<SeatType> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}