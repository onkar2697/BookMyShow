
package org.example.bookmyshow.theatreservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenRequestDTO {

    @NotBlank(message = "Screen name is required")
    @Size(max = 100)
    private String name;

    @NotNull(message = "Theatre ID is required")
    @Positive(message = "Theatre ID must be positive")
    private Long theatreId;

    @NotNull(message = "Screen type ID is required")
    @Positive(message = "Screen type ID must be positive")
    private Long screenTypeId;
}
