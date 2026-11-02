
package org.example.bookmyshow.theatreservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreRequestDTO {

    @NotBlank(message = "Theatre name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 500)
    private String address;

    @Size(max = 150)
    private String landmark;

    @NotNull(message = "City ID is required")
    @Positive(message = "City ID must be positive")
    private Long cityId;
}
