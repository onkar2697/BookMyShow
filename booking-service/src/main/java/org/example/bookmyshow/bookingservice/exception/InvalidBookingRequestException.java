package org.example.bookmyshow.bookingservice.exception;

public class InvalidBookingRequestException extends RuntimeException {

    public InvalidBookingRequestException(String message) {
        super(message);
    }
}