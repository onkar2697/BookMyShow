package org.example.bookmyshow.theatreservice.service;



import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.SeatRequestDTO;
import org.example.bookmyshow.theatreservice.dto.SeatResponseDTO;
import org.example.bookmyshow.theatreservice.entity.Screen;
import org.example.bookmyshow.theatreservice.entity.Seat;
import org.example.bookmyshow.theatreservice.entity.SeatType;
import org.example.bookmyshow.theatreservice.mapper.SeatMapper;
import org.example.bookmyshow.theatreservice.repository.ScreenRepository;
import org.example.bookmyshow.theatreservice.repository.SeatRepository;
import org.example.bookmyshow.theatreservice.repository.SeatTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SeatService {

    public final SeatRepository seatRepository;
    public final SeatTypeRepository seatTypeRepository;
    public final ScreenRepository screenRepository;

    @Transactional
    public SeatResponseDTO createSeat(SeatRequestDTO dto) {

        Screen screen = screenRepository.findById(dto.getScreenId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Screen with id " + dto.getScreenId() + " not found"
                ));

        if (!screen.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot create seat for inactive screen"
            );
        }

        SeatType seatType = seatTypeRepository.findById(dto.getSeatTypeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat type with id " + dto.getSeatTypeId() + " not found"
                ));

        if (!seatType.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot create seat with inactive seat type"
            );
        }

        if (seatRepository.existsByScreen_IdAndSeatNumberIgnoreCase(
                dto.getScreenId(), dto.getSeatNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Seat number already exists in this screen"
            );
        }

        if (seatRepository.existsByScreen_IdAndRowNumberAndColumnNumber(
                dto.getScreenId(), dto.getRowNumber(), dto.getColumnNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Seat already exists at this row and column"
            );
        }

        Seat seat = SeatMapper.toEntity(dto, screen, seatType);
        Seat savedSeat = seatRepository.save(seat);

        screen.setTotalSeats(screen.getTotalSeats() + 1);

        return SeatMapper.toResponseDTO(savedSeat);
    }

    @Transactional(readOnly = true)
    public SeatResponseDTO getSeatById(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "seat with id " + id + "not found"
                ));

        return SeatMapper.toResponseDTO(seat);
    }

    @Transactional(readOnly = true)
    public Page<SeatResponseDTO> getAllSeats(Pageable pageable){
        return seatRepository.findAll(pageable).map(SeatMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<SeatResponseDTO> getSeatsByScreen(Long screenId, Pageable pageable) {

        if (!screenRepository.existsById(screenId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Screen with id " + screenId + " not found"
            );
        }

        return seatRepository.findByScreen_Id(screenId, pageable)
                .map(SeatMapper::toResponseDTO);
    }

    @Transactional
    public SeatResponseDTO updateSeat(Long id, SeatRequestDTO dto) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat with id " + id + " not found"
                ));

        Screen oldScreen = seat.getScreen();

        Screen newScreen = screenRepository.findById(dto.getScreenId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Screen with id " + dto.getScreenId() + " not found"
                ));

        if (!newScreen.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot assign seat to inactive screen"
            );
        }

        SeatType seatType = seatTypeRepository.findById(dto.getSeatTypeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat type with id " + dto.getSeatTypeId() + " not found"
                ));

        if (!seatType.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot assign inactive seat type"
            );
        }

        if (seatRepository.existsByScreen_IdAndSeatNumberIgnoreCaseAndIdNot(
                dto.getScreenId(), dto.getSeatNumber(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Seat number already exists in this screen"
            );
        }

        if (seatRepository.existsByScreen_IdAndRowNumberAndColumnNumberAndIdNot(
                dto.getScreenId(), dto.getRowNumber(), dto.getColumnNumber(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Another seat already exists at this row and column"
            );
        }

        SeatMapper.updateEntity(seat, dto, newScreen, seatType);

        if (!oldScreen.getId().equals(newScreen.getId())) {
            oldScreen.setTotalSeats(oldScreen.getTotalSeats() - 1);
            newScreen.setTotalSeats(newScreen.getTotalSeats() + 1);
        }

        return SeatMapper.toResponseDTO(seat);
    }

    @Transactional
    public void deactivateSeat(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat with id " + id + " not found"
                ));

        // Avoid decrementing the count more than once.
        if (seat.isActive()) {
            Screen screen = seat.getScreen();
            screen.setTotalSeats(screen.getTotalSeats() - 1);
            seat.setActive(false);
        }
    }

}
