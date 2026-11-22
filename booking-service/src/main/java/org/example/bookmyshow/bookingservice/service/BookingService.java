package org.example.bookmyshow.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.bookingservice.dto.BookingRequest;
import org.example.bookmyshow.bookingservice.dto.BookingResponse;
import org.example.bookmyshow.bookingservice.entity.Booking;
import org.example.bookmyshow.bookingservice.entity.SeatLock;
import org.example.bookmyshow.bookingservice.enums.BookingStatus;
import org.example.bookmyshow.bookingservice.enums.SeatLockStatus;
import org.example.bookmyshow.bookingservice.mapper.BookingMapper;
import org.example.bookmyshow.bookingservice.repository.BookingRepository;
import org.example.bookmyshow.bookingservice.repository.BookingSeatRepository;
import org.example.bookmyshow.bookingservice.repository.SeatLockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

   private final BookingRepository bookingRepository;
   private final BookingMapper bookingMapper;
   private final BookingSeatRepository bookingSeatRepository;
   private final SeatLockRepository seatLockRepository;

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with id: " + id));

        return bookingMapper.toBookingResponse(booking);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingByReference(String reference) {

        Booking booking = bookingRepository.findByBookingReference(reference)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found: " + reference));

        return bookingMapper.toBookingResponse(booking);
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> getBookingsByUserId(
            Long userId, Pageable pageable) {

        return bookingRepository
                .findAllByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(bookingMapper::toBookingResponse);
    }

    private void validateSeatAvailability(
            Long showId,
            Long userId,
            List<Long> seatIds) {

        for (Long seatId : seatIds) {

            // Check whether the seat is already booked
            boolean alreadyBooked =
                    bookingSeatRepository
                            .existsByBooking_ShowIdAndBooking_StatusAndSeatId(
                                    showId,
                                    BookingStatus.CONFIRMED,
                                    seatId
                            );

            if (alreadyBooked) {
                throw new IllegalStateException(
                        "Seat " + seatId + " is already booked"
                );
            }

            // Check whether another user currently holds the lock
            List<SeatLock> activeLocks =
                    seatLockRepository
                            .findAllByShowIdAndSeatIdAndStatusAndExpiresAtAfter(
                                    showId,
                                    seatId,
                                    SeatLockStatus.LOCKED,
                                    LocalDateTime.now()
                            );

            boolean lockedByAnotherUser = activeLocks.stream()
                    .anyMatch(lock -> !lock.getUserId().equals(userId));

            if (lockedByAnotherUser) {
                throw new IllegalStateException(
                        "Seat " + seatId + " is currently locked by another user"
                );
            }
        }
    }

}
