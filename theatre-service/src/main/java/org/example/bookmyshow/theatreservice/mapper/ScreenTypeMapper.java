package org.example.bookmyshow.theatreservice.mapper;

import org.example.bookmyshow.theatreservice.dto.ScreenTypeRequestDTO;
import org.example.bookmyshow.theatreservice.dto.ScreenTypeResponseDTO;
import org.example.bookmyshow.theatreservice.entity.ScreenType;

public class ScreenTypeMapper {

    private ScreenTypeMapper() {}

    public static ScreenType toEntity(ScreenTypeRequestDTO dto) {
        ScreenType screenType = new ScreenType();
        screenType.setCode(dto.getCode());
        screenType.setDisplayName(dto.getDisplayName());
        screenType.setDescription(dto.getDescription());
        return screenType;
    }

    public static ScreenTypeResponseDTO toResponseDTO(ScreenType screenType) {
        ScreenTypeResponseDTO dto = new ScreenTypeResponseDTO();
        dto.setId(screenType.getId());
        dto.setCode(screenType.getCode());
        dto.setDisplayName(screenType.getDisplayName());
        dto.setDescription(screenType.getDescription());
        dto.setActive(screenType.isActive());
        return dto;
    }

    public static void updateEntity(
            ScreenType screenType,
            ScreenTypeRequestDTO dto) {

        screenType.setCode(dto.getCode());
        screenType.setDisplayName(dto.getDisplayName());
        screenType.setDescription(dto.getDescription());
    }
}