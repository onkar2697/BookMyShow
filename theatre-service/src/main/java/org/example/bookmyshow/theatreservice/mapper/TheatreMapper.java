package org.example.bookmyshow.theatreservice.mapper;

import org.example.bookmyshow.theatreservice.dto.TheatreRequestDTO;
import org.example.bookmyshow.theatreservice.dto.TheatreUpdateDTO;
import org.example.bookmyshow.theatreservice.dto.TheatreResponseDTO;
import org.example.bookmyshow.theatreservice.entity.City;
import org.example.bookmyshow.theatreservice.entity.Theatre;

public class TheatreMapper {

    private TheatreMapper() {}

    public static Theatre toEntity(TheatreRequestDTO dto, City city) {
        Theatre theatre = new Theatre();
        theatre.setName(dto.getName());
        theatre.setAddress(dto.getAddress());
        theatre.setLandmark(dto.getLandmark());
        theatre.setCity(city);
        return theatre;
    }

    public static TheatreResponseDTO toResponseDTO(Theatre theatre) {
        TheatreResponseDTO dto = new TheatreResponseDTO();
        dto.setId(theatre.getId());
        dto.setName(theatre.getName());
        dto.setAddress(theatre.getAddress());
        dto.setLandmark(theatre.getLandmark());
        dto.setActive(theatre.isActive());
        dto.setCityId(theatre.getCity().getId());
        dto.setCityName(theatre.getCity().getName());
        return dto;
    }

    public static void updateEntity(
            Theatre theatre,
            TheatreUpdateDTO dto,
            City city) {

        theatre.setName(dto.getName());
        theatre.setAddress(dto.getAddress());
        theatre.setLandmark(dto.getLandmark());
        theatre.setCity(city);
    }
}