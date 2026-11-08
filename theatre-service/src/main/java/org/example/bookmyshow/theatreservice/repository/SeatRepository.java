package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByScreen_Id(Long screenId);

    List<Seat> findByScreen_IdAndActiveTrue(Long screenId);

    boolean existsByScreen_IdAndSeatNumberIgnoreCase(
            Long screenId, String seatNumber
    );

    boolean existsByScreen_IdAndRowNumberAndColumnNumber(
            Long screenId, Integer rowNumber, Integer columnNumber
    );
}
