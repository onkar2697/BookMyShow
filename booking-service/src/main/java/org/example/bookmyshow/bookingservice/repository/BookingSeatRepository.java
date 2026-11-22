
package org.example.bookmyshow.bookingservice.repository;

import org.example.bookmyshow.bookingservice.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

    List<BookingSeat> findAllByBooking_Id(Long bookingId);

    boolean existsByBooking_ShowIdAndSeatId(Long showId, Long seatId);
}
