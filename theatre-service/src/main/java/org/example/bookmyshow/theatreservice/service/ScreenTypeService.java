package org.example.bookmyshow.theatreservice.service;


import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.ScreenTypeRequestDTO;
import org.example.bookmyshow.theatreservice.dto.ScreenTypeResponseDTO;
import org.example.bookmyshow.theatreservice.entity.ScreenType;
import org.example.bookmyshow.theatreservice.mapper.ScreenTypeMapper;
import org.example.bookmyshow.theatreservice.repository.CityRepository;
import org.example.bookmyshow.theatreservice.repository.ScreenTypeRepository;
import org.example.bookmyshow.theatreservice.repository.TheatreRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScreenTypeService {

    private final ScreenTypeRepository screenTypeRepository;
    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;

    @Transactional
    public ScreenTypeResponseDTO createScreenType(ScreenTypeRequestDTO dto) {

        if (screenTypeRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Screen type already exists with code: " + dto.getCode()
            );
        }

        ScreenType screenType = ScreenTypeMapper.toEntity(dto);

        ScreenType savedScreenType = screenTypeRepository.save(screenType);

        return ScreenTypeMapper.toResponseDTO(savedScreenType);
    }


//    getScreenTypeById(Long screenTypeId)
//    getAllScreenTypes(Pageable pageable)
//    updateScreenType(Long screenTypeId, ScreenTypeRequestDTO dto)
//    deactivateScreenType(Long screenTypeId)
}
