package org.example.bookmyshow.theatreservice.mapper;

import org.example.bookmyshow.theatreservice.dto.ScreenRequestDTO;
import org.example.bookmyshow.theatreservice.dto.ScreenResponseDTO;
import org.example.bookmyshow.theatreservice.entity.Screen;
import org.example.bookmyshow.theatreservice.entity.ScreenType;
import org.example.bookmyshow.theatreservice.entity.Theatre;

public class ScreenMapper {

    private ScreenMapper() {}

    public static Screen toEntity(
            ScreenRequestDTO dto,
            Theatre theatre,
            ScreenType screenType) {

        Screen screen = new Screen();
        screen.setName(dto.getName());
        screen.setTheatre(theatre);
        screen.setScreenType(screenType);
        return screen;
    }

    public static ScreenResponseDTO toResponseDTO(Screen screen) {
        ScreenResponseDTO dto = new ScreenResponseDTO();
        dto.setId(screen.getId());
        dto.setName(screen.getName());
        dto.setTotalSeats(screen.getTotalSeats());
        dto.setActive(screen.isActive());
        dto.setTheatreId(screen.getTheatre().getId());
        dto.setTheatreName(screen.getTheatre().getName());
        dto.setScreenTypeId(screen.getScreenType().getId());
        dto.setScreenType(screen.getScreenType().getDisplayName());
        return dto;
    }

    public static void updateEntity(
            Screen screen,
            ScreenRequestDTO dto,
            Theatre theatre,
            ScreenType screenType) {

        screen.setName(dto.getName());
        screen.setTheatre(theatre);
        screen.setScreenType(screenType);
    }
}