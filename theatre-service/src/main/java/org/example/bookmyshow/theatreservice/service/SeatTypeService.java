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
        return seatTypeRepository.findAll(pageable).map(SeatTypeMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public SeatTypeResponseDTO getSeatTypeById(Long id) {
        SeatType seatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat type with id " + id + " not found"
                ));

        return SeatTypeMapper.toResponseDTO(seatType);
    }

    @Transactional
    public SeatTypeResponseDTO updateSeatType(Long id, SeatTypeRequestDTO dto){

        SeatType seatType= seatTypeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat type with id "+ id +" not found"
                ));

        seatTypeRepository.findByCodeIgnoreCase(dto.getCode())
                        .ifPresent(existing ->{
                            if(!existing.getId().equals(id)){
                                throw new ResponseStatusException(
                                        HttpStatus.CONFLICT,
                                        "Seat type alrrady exists with code : " +dto.getCode()
                                );
                            }
                        });
        SeatTypeMapper.updateEntity(seatType,dto);
        return SeatTypeMapper.toResponseDTO(seatType);
    }

    @Transactional
    public void deactivateSeatType(Long id) {
        SeatType seatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat type with id " + id + " not found"
                ));
        seatType.setActive(false);
    }

}
