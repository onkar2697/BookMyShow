package org.example.bookmyshow.theatreservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.CityRequestDTO;
import org.example.bookmyshow.theatreservice.dto.CityResponseDTO;
import org.example.bookmyshow.theatreservice.entity.City;
import org.example.bookmyshow.theatreservice.mapper.CityMapper;
import org.example.bookmyshow.theatreservice.repository.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    @Transactional
    public CityResponseDTO createCity(CityRequestDTO dto) {

        boolean cityExists = cityRepository
                .existsByNameIgnoreCaseAndStateIgnoreCaseAndCountryIgnoreCase(
                        dto.getName(),
                        dto.getState(),
                        dto.getCountry()
                );

        if (cityExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "City already exists"
            );
        }

        City city = CityMapper.toEntity(dto);

        City savedCity = cityRepository.save(city);

        return CityMapper.toResponseDTO(savedCity);
    }

    @Transactional(readOnly = true)
    public CityResponseDTO getCityById(Long cityId) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "City not found with ID: " + cityId
                ));

        return CityMapper.toResponseDTO(city);
    }

    @Transactional(readOnly = true)
    public List<CityResponseDTO> getAllCities() {

        return cityRepository.findAll()
                .stream()
                .map(CityMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public CityResponseDTO updateCity(Long cityId, CityRequestDTO dto) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "City not found with ID: " + cityId
                ));

        cityRepository
                .findByNameIgnoreCaseAndStateIgnoreCaseAndCountryIgnoreCase(
                        dto.getName(),
                        dto.getState(),
                        dto.getCountry()
                )
                .ifPresent(existingCity -> {
                    if (!existingCity.getId().equals(cityId)) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "City already exists"
                        );
                    }
                });

        CityMapper.updateEntity(city, dto);

        City updatedCity = cityRepository.save(city);

        return CityMapper.toResponseDTO(updatedCity);
    }
}