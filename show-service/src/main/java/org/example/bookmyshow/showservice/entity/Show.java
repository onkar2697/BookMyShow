
package org.example.bookmyshow.showservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
@Getter
@Setter
@NoArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;

    private Long screenId;

    private Long screenTypeId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String language;

    private Integer bufferMinutes = 15;

    @Enumerated(EnumType.STRING)
    private ShowStatus status = ShowStatus.SCHEDULED;

    @Version
    private Long version;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;

        if (status == null) {
            status = ShowStatus.SCHEDULED;
        }

        if (bufferMinutes == null) {
            bufferMinutes = 15;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
