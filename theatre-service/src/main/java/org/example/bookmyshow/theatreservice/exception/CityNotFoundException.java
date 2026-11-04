package org.example.bookmyshow.theatreservice.exception;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String message) {
        super(message);
    }
}
