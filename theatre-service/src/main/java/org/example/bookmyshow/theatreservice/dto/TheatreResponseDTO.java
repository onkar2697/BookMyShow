package org.example.bookmyshow.theatreservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String landmark;
    private boolean active;

    private Long cityId;
    private String cityName;
}
