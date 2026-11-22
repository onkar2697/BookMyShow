
package org.example.bookmyshow.bookingservice.entity;

import org.example.bookmyshow.bookingservice.enums.SeatLockStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat_locks")
@Getter
@Setter
@NoArgsConstructor
public class SeatLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long showId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private Long userId;

    private Long bookingId;

    @Column(nullable = false)
    private LocalDateTime lockedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatLockStatus status;

    @PrePersist
    public void onCreate() {
        this.lockedAt = LocalDateTime.now();

        if (this.expiresAt == null) {
            this.expiresAt = LocalDateTime.now().plusMinutes(5);
        }

        if (this.status == null) {
            this.status = SeatLockStatus.LOCKED;
        }
    }
}
