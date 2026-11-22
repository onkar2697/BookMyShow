
package org.example.bookmyshow.bookingservice.repository;

import org.example.bookmyshow.bookingservice.entity.BookingSeat;
import org.example.bookmyshow.bookingservice.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

    List<BookingSeat> findAllByBooking_Id(Long bookingId);

    boolean existsByBooking_ShowIdAndBooking_StatusAndSeatId(
            Long showId,
            BookingStatus status,
            Long seatId
    );
}
