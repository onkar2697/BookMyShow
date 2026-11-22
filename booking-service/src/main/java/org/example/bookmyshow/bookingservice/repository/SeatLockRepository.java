
package org.example.bookmyshow.bookingservice.repository;

import org.example.bookmyshow.bookingservice.entity.SeatLock;
import org.example.bookmyshow.bookingservice.enums.SeatLockStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatLockRepository extends JpaRepository<SeatLock, Long> {

    List<SeatLock> findAllByShowIdAndSeatIdAndStatusAndExpiresAtAfter(
            Long showId,
            Long seatId,
            SeatLockStatus status,
            LocalDateTime currentTime
    );

    List<SeatLock> findAllByUserIdAndStatusAndExpiresAtAfter(
            Long userId,
            SeatLockStatus status,
            LocalDateTime currentTime
    );

    List<SeatLock> findAllByExpiresAtBeforeAndStatus(
            LocalDateTime currentTime,
            SeatLockStatus status
    );

    List<SeatLock> findAllByBookingId(Long bookingId);
}
