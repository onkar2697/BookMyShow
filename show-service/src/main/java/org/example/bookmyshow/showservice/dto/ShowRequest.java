
package org.example.bookmyshow.showservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowRequest {

    @NotNull
    @Positive
    private Long movieId;

    @NotNull
    @Positive
    private Long screenId;

    @NotNull
    @Positive
    private Long screenTypeId;

    @NotNull
    @Future
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotBlank
    private String language;

    @PositiveOrZero
    private Integer bufferMinutes = 15;
}
