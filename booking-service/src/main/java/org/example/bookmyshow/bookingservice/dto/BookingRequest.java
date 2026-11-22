
package org.example.bookmyshow.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookingRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long showId;

    @NotEmpty
    @Size(max = 10)
    private List<Long> seatIds;
}
