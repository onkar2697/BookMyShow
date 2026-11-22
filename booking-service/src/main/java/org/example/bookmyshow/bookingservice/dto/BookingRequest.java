package org.example.bookmyshow.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Show ID is required")
    @Positive(message = "Show ID must be positive")
    private Long showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<
            @NotNull(message = "Seat ID cannot be null")
            @Positive(message = "Seat ID must be positive")
                    Long
            > seatIds;
}