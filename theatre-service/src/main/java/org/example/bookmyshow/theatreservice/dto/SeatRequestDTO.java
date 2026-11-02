
package org.example.bookmyshow.theatreservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatRequestDTO {

    @NotBlank(message = "Seat number is required")
    @Size(max = 20)
    private String seatNumber;

    @NotNull(message = "Row number is required")
    @Positive(message = "Row number must be positive")
    private Integer rowNumber;

    @NotNull(message = "Column number is required")
    @Positive(message = "Column number must be positive")
    private Integer columnNumber;

    @NotNull(message = "Screen ID is required")
    @Positive(message = "Screen ID must be positive")
    private Long screenId;

    @NotNull(message = "Seat type ID is required")
    @Positive(message = "Seat type ID must be positive")
    private Long seatTypeId;
}
