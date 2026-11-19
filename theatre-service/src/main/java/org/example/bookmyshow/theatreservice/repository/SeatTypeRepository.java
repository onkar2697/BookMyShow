package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {

    Optional<SeatType> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}