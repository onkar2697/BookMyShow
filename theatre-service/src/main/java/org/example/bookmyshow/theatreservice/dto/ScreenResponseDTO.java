package org.example.bookmyshow.theatreservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenResponseDTO {

    private Long id;
    private String name;
    private Integer totalSeats;
    private boolean active;

    private Long theatreId;
    private String theatreName;

    private Long screenTypeId;
    private String screenType;
}
