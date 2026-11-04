package org.example.bookmyshow.theatreservice.exception;

public class TheatreNotFoundException extends RuntimeException{
    public TheatreNotFoundException(String message) {
        super(message);
    }
}
