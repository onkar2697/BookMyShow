package org.example.bookmyshow.theatreservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatResponseDTO {

    private Long id;
    private String seatNumber;
    private Integer rowNumber;
    private Integer columnNumber;
    private boolean active;

    private Long screenId;
    private String screenName;

    private Long seatTypeId;
    private String seatType;
}
