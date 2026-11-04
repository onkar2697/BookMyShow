package org.example.bookmyshow.theatreservice.exception;

public class SeatNotFoundException extends RuntimeException{
    public SeatNotFoundException(String message) {
        super(message);
    }
}
