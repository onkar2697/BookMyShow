package org.example.bookmyshow.theatreservice.mapper;

import org.example.bookmyshow.theatreservice.dto.SeatRequestDTO;
import org.example.bookmyshow.theatreservice.dto.SeatResponseDTO;
import org.example.bookmyshow.theatreservice.entity.Screen;
import org.example.bookmyshow.theatreservice.entity.Seat;
import org.example.bookmyshow.theatreservice.entity.SeatType;

public class SeatMapper {

    private SeatMapper() {}

    public static Seat toEntity(
            SeatRequestDTO dto,
            Screen screen,
            SeatType seatType) {

        Seat seat = new Seat();
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setRowNumber(dto.getRowNumber());
        seat.setColumnNumber(dto.getColumnNumber());
        seat.setScreen(screen);
        seat.setSeatType(seatType);
        return seat;
    }

    public static SeatResponseDTO toResponseDTO(Seat seat) {
        SeatResponseDTO dto = new SeatResponseDTO();
        dto.setId(seat.getId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setRowNumber(seat.getRowNumber());
        dto.setColumnNumber(seat.getColumnNumber());
        dto.setActive(seat.isActive());
        dto.setScreenId(seat.getScreen().getId());
        dto.setScreenName(seat.getScreen().getName());
        dto.setSeatTypeId(seat.getSeatType().getId());
        dto.setSeatType(seat.getSeatType().getDisplayName());
        return dto;
    }

    public static void updateEntity(
            Seat seat,
            SeatRequestDTO dto,
            Screen screen,
            SeatType seatType) {

        seat.setSeatNumber(dto.getSeatNumber());
        seat.setRowNumber(dto.getRowNumber());
        seat.setColumnNumber(dto.getColumnNumber());
        seat.setScreen(screen);
        seat.setSeatType(seatType);
    }
}