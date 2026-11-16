
package org.example.bookmyshow.theatreservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.TheatreRequestDTO;
import org.example.bookmyshow.theatreservice.dto.TheatreUpdateDTO;
import org.example.bookmyshow.theatreservice.dto.TheatreResponseDTO;
import org.example.bookmyshow.theatreservice.entity.City;
import org.example.bookmyshow.theatreservice.entity.Theatre;
import org.example.bookmyshow.theatreservice.mapper.TheatreMapper;
import org.example.bookmyshow.theatreservice.repository.CityRepository;
import org.example.bookmyshow.theatreservice.repository.TheatreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;

    @Transactional
    public TheatreResponseDTO createTheatre(TheatreRequestDTO dto) {

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "City not found with ID: " + dto.getCityId()
                ));

        boolean theatreExists = theatreRepository
                .existsByNameIgnoreCaseAndCity_Id(
                        dto.getName(), dto.getCityId()
                );

        if (theatreExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Theatre already exists in this city"
            );
        }

        Theatre theatre = TheatreMapper.toEntity(dto, city);

        Theatre savedTheatre = theatreRepository.save(theatre);

        return TheatreMapper.toResponseDTO(savedTheatre);
    }

    @Transactional(readOnly = true)
    public TheatreResponseDTO getTheatreById(Long theatreId) {

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Theatre not found with ID: " + theatreId
                ));

        return TheatreMapper.toResponseDTO(theatre);
    }

    @Transactional(readOnly = true)
    public Page<TheatreResponseDTO> getAllTheatres(Pageable pageable) {

        return theatreRepository.findAll(pageable)
                .map(TheatreMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<TheatreResponseDTO> getTheatresByCity(
            Long cityId, Pageable pageable) {

        if (!cityRepository.existsById(cityId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "City not found with ID: " + cityId
            );
        }

        return theatreRepository.findByCity_Id(cityId, pageable)
                .map(TheatreMapper::toResponseDTO);
    }

    @Transactional
    public TheatreResponseDTO updateTheatre(
            Long theatreId, TheatreUpdateDTO dto) {

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Theatre not found with ID: " + theatreId
                ));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "City not found with ID: " + dto.getCityId()
                ));

        theatreRepository.findByNameIgnoreCaseAndCity_Id(
                dto.getName(), dto.getCityId()
        ).ifPresent(existingTheatre -> {
            if (!existingTheatre.getId().equals(theatreId)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Theatre already exists in this city"
                );
            }
        });

        TheatreMapper.updateEntity(theatre, dto, city);

        return TheatreMapper.toResponseDTO(theatre);
    }

    @Transactional
    public void deactivateTheatre(Long theatreId) {

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Theatre not found with ID: " + theatreId
                ));

        theatre.setActive(false);
    }
}
