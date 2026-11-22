package org.example.bookmyshow.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.bookingservice.dto.BookingRequest;
import org.example.bookmyshow.bookingservice.dto.BookingResponse;
import org.example.bookmyshow.bookingservice.entity.Booking;
import org.example.bookmyshow.bookingservice.mapper.BookingMapper;
import org.example.bookmyshow.bookingservice.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingService {

   private final BookingRepository bookingRepository;
   private final BookingMapper bookingMapper;

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

}
