package org.example.bookmyshow.theatreservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResponseDTO {

    private Long id;
    private String name;
    private String state;
    private String country;
    private String pincode;
}

