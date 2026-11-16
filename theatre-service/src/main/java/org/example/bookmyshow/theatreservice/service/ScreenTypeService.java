package org.example.bookmyshow.theatreservice.service;


import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.ScreenTypeRequestDTO;
import org.example.bookmyshow.theatreservice.dto.ScreenTypeResponseDTO;
import org.example.bookmyshow.theatreservice.entity.ScreenType;
import org.example.bookmyshow.theatreservice.mapper.ScreenTypeMapper;
import org.example.bookmyshow.theatreservice.repository.ScreenTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScreenTypeService {

    private final ScreenTypeRepository screenTypeRepository;


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

    @Transactional(readOnly = true)
    public ScreenTypeResponseDTO getScreenTypeById(Long screenTypeId) {

        ScreenType screenType = screenTypeRepository.findById(screenTypeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Screen type with id: " + screenTypeId + " does not exist"
                ));

        return ScreenTypeMapper.toResponseDTO(screenType);
    }

    @Transactional(readOnly = true)
    public Page<ScreenTypeResponseDTO> getAllScreenTypes(Pageable pageable){
        return  screenTypeRepository.findAll(pageable).map(ScreenTypeMapper::toResponseDTO);
    }

    @Transactional
    public ScreenTypeResponseDTO updateScreenType(
            Long screenTypeId, ScreenTypeRequestDTO dto) {

        ScreenType screenType = screenTypeRepository.findById(screenTypeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Screen type with id: " + screenTypeId + " does not exist"
                ));

        screenTypeRepository.findByCodeIgnoreCase(dto.getCode())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(screenTypeId)) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Screen type already exists with code: " + dto.getCode()
                        );
                    }
                });

        ScreenTypeMapper.updateEntity(screenType, dto);

        return ScreenTypeMapper.toResponseDTO(screenType);
    }

    @Transactional
    public void deactivateScreenType(Long screenTypeId) {

        ScreenType screenType = screenTypeRepository.findById(screenTypeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Screen type with id: " + screenTypeId + " does not exist"
                ));

        screenType.setActive(false);
    }
}
