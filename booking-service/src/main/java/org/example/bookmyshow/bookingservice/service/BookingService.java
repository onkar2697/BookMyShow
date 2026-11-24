
package org.example.bookmyshow.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.bookingservice.dto.BookingRequest;
import org.example.bookmyshow.bookingservice.dto.BookingResponse;
import org.example.bookmyshow.bookingservice.entity.Booking;
import org.example.bookmyshow.bookingservice.entity.BookingSeat;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatLockRepository seatLockRepository;

    // Get booking by ID
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with id: " + id));

        return bookingMapper.toBookingResponse(booking);
    }

    // Get booking by reference
    @Transactional(readOnly = true)
    public BookingResponse getBookingByReference(String reference) {

        Booking booking = bookingRepository.findByBookingReference(reference)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found: " + reference));

        return bookingMapper.toBookingResponse(booking);
    }

    // Get bookings by user ID with pagination
    @Transactional(readOnly = true)
    public Page<BookingResponse> getBookingsByUserId(
            Long userId, Pageable pageable) {

        return bookingRepository
                .findAllByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(bookingMapper::toBookingResponse);
    }

    // Validate the duplicate seats
    private void validateDuplicateSeats(List<Long> seatIds) {

        if (seatIds.stream().distinct().count() != seatIds.size()) {
            throw new IllegalArgumentException(
                    "Duplicate seats are not allowed"
            );
        }
    }

    // Validate seat availability
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

    // Create seat locks
    private void createSeatLocks(
            Long showId,
            Long userId,
            List<Long> seatIds) {

        LocalDateTime now = LocalDateTime.now();

        for (Long seatId : seatIds) {

            SeatLock seatLock = new SeatLock();
            seatLock.setShowId(showId);
            seatLock.setSeatId(seatId);
            seatLock.setUserId(userId);
            seatLock.setStatus(SeatLockStatus.LOCKED);
            seatLock.setLockedAt(now);
            seatLock.setExpiresAt(now.plusMinutes(5));

            seatLockRepository.save(seatLock);
        }
    }

    // Create booking
    @Transactional
    public BookingResponse createBooking(
            BookingRequest request,
            Map<Long, BigDecimal> seatPrices) {

        // Validate duplicate seats
        validateDuplicateSeats(request.getSeatIds());

        //Validate seat prices
        if (seatPrices == null) {
            throw new IllegalArgumentException(
                    "Seat prices are required"
            );
        }

        for (Long seatId : request.getSeatIds()) {

            BigDecimal price = seatPrices.get(seatId);

            if (price == null || price.signum() < 0) {
                throw new IllegalArgumentException(
                        "Invalid price for seat: " + seatId
                );
            }
        }

        //Check seat availability
        validateSeatAvailability(
                request.getShowId(),
                request.getUserId(),
                request.getSeatIds()
        );

        //Create seat locks
        createSeatLocks(
                request.getShowId(),
                request.getUserId(),
                request.getSeatIds()
        );

        //Create booking
        Booking booking = new Booking();
        booking.setBookingReference(UUID.randomUUID().toString());
        booking.setUserId(request.getUserId());
        booking.setShowId(request.getShowId());
        booking.setStatus(BookingStatus.PENDING);

        // Create booking seats and calculate total
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Long seatId : request.getSeatIds()) {

            BigDecimal price = seatPrices.get(seatId);

            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setSeatId(seatId);
            bookingSeat.setSeatNumber(String.valueOf(seatId));
            bookingSeat.setPrice(price);

            booking.addBookingSeat(bookingSeat);

            totalAmount = totalAmount.add(price);
        }

        // 7. Set total amount
        booking.setTotalAmount(totalAmount);

        // 8. Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // 9. Return response
        return bookingMapper.toBookingResponse(savedBooking);
    }
}
