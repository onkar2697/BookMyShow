package org.example.bookmyshow.bookingservice.exception;

public class SeatAlreadyBookedException extends RuntimeException {

    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}