
package org.example.bookmyshow.theatreservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatTypeRequestDTO {

    @NotBlank(message = "Seat type code is required")
    @Size(max = 50)
    private String code;

    @NotBlank(message = "Seat type display name is required")
    @Size(max = 100)
    private String displayName;

    @Size(max = 255)
    private String description;
}
