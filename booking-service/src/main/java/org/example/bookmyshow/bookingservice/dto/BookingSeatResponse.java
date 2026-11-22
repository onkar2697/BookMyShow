
package org.example.bookmyshow.bookingservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BookingSeatResponse {

    private Long id;

    private Long seatId;

    private String seatNumber;

    private BigDecimal price;
}
