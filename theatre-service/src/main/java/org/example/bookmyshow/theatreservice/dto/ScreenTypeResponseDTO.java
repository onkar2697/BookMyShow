package org.example.bookmyshow.theatreservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenTypeResponseDTO {

    private Long id;
    private String code;
    private String displayName;
    private String description;
    private boolean active;
}
