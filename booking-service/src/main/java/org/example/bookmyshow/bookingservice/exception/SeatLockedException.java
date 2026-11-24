package org.example.bookmyshow.bookingservice.exception;

public class SeatLockedException extends RuntimeException {

    public SeatLockedException(String message) {
        super(message);
    }
}