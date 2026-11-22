package org.example.bookmyshow.bookingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bookmyshow.bookingservice.enums.SeatLockStatus;

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

    @NotNull
    @Column(nullable = false)
    private Long showId;

    @NotNull
    @Column(nullable = false)
    private Long seatId;

    @NotNull
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
        lockedAt = LocalDateTime.now();
        expiresAt = lockedAt.plusMinutes(5);

        if (status == null) {
            status = SeatLockStatus.LOCKED;
        }
    }
}