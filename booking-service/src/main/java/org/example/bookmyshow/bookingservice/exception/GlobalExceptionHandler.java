package org.example.bookmyshow.bookingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status, String message) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookingNotFound(
            BookingNotFoundException ex) {

        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SeatAlreadyBookedException.class)
    public ResponseEntity<Map<String, Object>> handleSeatAlreadyBooked(
            SeatAlreadyBookedException ex) {

        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(SeatLockedException.class)
    public ResponseEntity<Map<String, Object>> handleSeatLocked(
            SeatLockedException ex) {

        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidBookingRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidBooking(
            InvalidBookingRequestException ex) {

        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
            Exception ex) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred"
        );
    }
}