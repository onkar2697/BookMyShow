package org.example.bookmyshow.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.bookingservice.dto.BookingResponse;
import org.example.bookmyshow.bookingservice.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

    // Get booking by reference
    @GetMapping("/reference/{reference}")
    public ResponseEntity<BookingResponse> getBookingByReference(
            @PathVariable String reference) {

        return ResponseEntity.ok(
                bookingService.getBookingByReference(reference)
        );
    }

    // Get bookings by user ID with pagination
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BookingResponse>> getBookingsByUserId(
            @PathVariable Long userId,
            Pageable pageable) {

        return ResponseEntity.ok(
                bookingService.getBookingsByUserId(userId, pageable)
        );
    }
}