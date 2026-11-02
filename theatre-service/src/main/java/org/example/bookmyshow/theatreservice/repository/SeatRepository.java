package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Long> {
}
