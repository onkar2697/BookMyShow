package org.example.bookmyshow.bookingservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowResponse {

    private Long id;
    private Long movieId;
    private Long screenId;
    private Long screenTypeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String language;
    private Integer bufferMinutes;
    private String status;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
