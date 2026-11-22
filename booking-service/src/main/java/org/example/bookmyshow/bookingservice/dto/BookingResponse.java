
package org.example.bookmyshow.bookingservice.dto;

import org.example.bookmyshow.bookingservice.enums.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {

    private Long id;

    private String bookingReference;

    private Long userId;

    private Long showId;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private LocalDateTime bookingTime;

    private List<BookingSeatResponse> bookingSeats;
}
