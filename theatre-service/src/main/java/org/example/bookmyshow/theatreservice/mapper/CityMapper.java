package org.example.bookmyshow.theatreservice.mapper;

import org.example.bookmyshow.theatreservice.dto.CityRequestDTO;
import org.example.bookmyshow.theatreservice.dto.CityResponseDTO;
import org.example.bookmyshow.theatreservice.entity.City;

public class CityMapper {

    private CityMapper() {
    }

    public static City toEntity(CityRequestDTO dto) {
        City city = new City();
        city.setName(dto.getName());
        city.setState(dto.getState());
        city.setCountry(dto.getCountry());
        city.setPincode(dto.getPincode());
        return city;
    }

    public static CityResponseDTO toResponseDTO(City city) {
        CityResponseDTO dto = new CityResponseDTO();
        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setState(city.getState());
        dto.setCountry(city.getCountry());
        dto.setPincode(city.getPincode());
        return dto;
    }

    public static void updateEntity(City city, CityRequestDTO dto) {
        city.setName(dto.getName());
        city.setState(dto.getState());
        city.setCountry(dto.getCountry());
        city.setPincode(dto.getPincode());
    }
}