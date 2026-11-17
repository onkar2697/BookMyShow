package org.example.bookmyshow.theatreservice.service;


import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.ScreenRequestDTO;
import org.example.bookmyshow.theatreservice.dto.ScreenResponseDTO;
import org.example.bookmyshow.theatreservice.entity.Screen;
import org.example.bookmyshow.theatreservice.entity.ScreenType;
import org.example.bookmyshow.theatreservice.entity.Theatre;
import org.example.bookmyshow.theatreservice.mapper.ScreenMapper;
import org.example.bookmyshow.theatreservice.repository.ScreenRepository;
import org.example.bookmyshow.theatreservice.repository.ScreenTypeRepository;
import org.example.bookmyshow.theatreservice.repository.TheatreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScreenService {
    private final ScreenTypeRepository screenTypeRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

    @Transactional
    public ScreenResponseDTO createScreen(ScreenRequestDTO dto) {

        Theatre theatre = theatreRepository.findById(dto.getTheatreId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Theatre not found with ID: " + dto.getTheatreId()
                ));

        if (!theatre.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot create screen for an inactive theatre"
            );
        }

        ScreenType screenType = screenTypeRepository.findById(dto.getScreenTypeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Screen type with ID: " + dto.getScreenTypeId() + " does not exist"
                ));

        if (!screenType.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Selected screen type is inactive"
            );
        }

        if (screenRepository.existsByNameIgnoreCaseAndTheatre_Id(
                dto.getName(), dto.getTheatreId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Screen already exists in this theatre"
            );
        }

        Screen screen = ScreenMapper.toEntity(dto, theatre, screenType);

        Screen savedScreen = screenRepository.save(screen);

        return ScreenMapper.toResponseDTO(savedScreen);
    }

}
