
package org.example.bookmyshow.bookingservice.dto;

import org.example.bookmyshow.bookingservice.enums.SeatLockStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SeatLockResponse {

    private Long id;

    private Long showId;

    private Long seatId;

    private Long userId;

    private LocalDateTime lockedAt;

    private LocalDateTime expiresAt;

    private SeatLockStatus status;
}
