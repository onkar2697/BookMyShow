package org.example.bookmyshow.theatreservice.mapper;

import org.example.bookmyshow.theatreservice.dto.SeatTypeRequestDTO;
import org.example.bookmyshow.theatreservice.dto.SeatTypeResponseDTO;
import org.example.bookmyshow.theatreservice.entity.SeatType;

public class SeatTypeMapper {

    private SeatTypeMapper() {}

    public static SeatType toEntity(SeatTypeRequestDTO dto) {
        SeatType seatType = new SeatType();
        seatType.setCode(dto.getCode());
        seatType.setDisplayName(dto.getDisplayName());
        seatType.setDescription(dto.getDescription());
        return seatType;
    }

    public static SeatTypeResponseDTO toResponseDTO(SeatType seatType) {
        SeatTypeResponseDTO dto = new SeatTypeResponseDTO();
        dto.setId(seatType.getId());
        dto.setCode(seatType.getCode());
        dto.setDisplayName(seatType.getDisplayName());
        dto.setDescription(seatType.getDescription());
        dto.setActive(seatType.isActive());
        return dto;
    }

    public static void updateEntity(
            SeatType seatType,
            SeatTypeRequestDTO dto) {

        seatType.setCode(dto.getCode());
        seatType.setDisplayName(dto.getDisplayName());
        seatType.setDescription(dto.getDescription());
    }
}