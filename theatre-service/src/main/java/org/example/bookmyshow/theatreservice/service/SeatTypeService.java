package org.example.bookmyshow.theatreservice.service;


import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.SeatTypeRequestDTO;
import org.example.bookmyshow.theatreservice.dto.SeatTypeResponseDTO;
import org.example.bookmyshow.theatreservice.entity.SeatType;
import org.example.bookmyshow.theatreservice.mapper.SeatTypeMapper;
import org.example.bookmyshow.theatreservice.repository.SeatTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;

    @Transactional
    public SeatTypeResponseDTO createSeatType(SeatTypeRequestDTO dto) {

        if(seatTypeRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "seat type is already exists with code " + dto.getCode()
            );
        }
        SeatType seatType = SeatTypeMapper.toEntity(dto);
        SeatType savedSeatType = seatTypeRepository.save(seatType);

        return SeatTypeMapper.toResponseDTO(savedSeatType);
    }

    @Transactional(readOnly = true)
    public Page<SeatTypeResponseDTO> getAllSeatTypes(Pageable pageable) {

    }

}
