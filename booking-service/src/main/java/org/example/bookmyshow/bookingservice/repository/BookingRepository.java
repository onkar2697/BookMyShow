
package org.example.bookmyshow.bookingservice.repository;

import org.example.bookmyshow.bookingservice.entity.Booking;
import org.example.bookmyshow.bookingservice.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingReference(String bookingReference);

    Page<Booking> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);


    Page<Booking> findAllByShowIdAndStatus(Long showId, BookingStatus status, Pageable pageable);
}
