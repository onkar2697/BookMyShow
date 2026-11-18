package org.example.bookmyshow.theatreservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.SeatTypeRequestDTO;
import org.example.bookmyshow.theatreservice.dto.SeatTypeResponseDTO;
import org.example.bookmyshow.theatreservice.entity.SeatType;
import org.example.bookmyshow.theatreservice.service.SeatTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seat-types")
public class SeatTypeController {

    private final SeatTypeService seatTypeService;

    @PostMapping
    public ResponseEntity<SeatTypeResponseDTO> createSeatType(@Valid @RequestBody SeatTypeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seatTypeService.createSeatType(request));
    }

    @GetMapping
    public ResponseEntity<Page<SeatTypeResponseDTO>> getAllSeatTypes(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(seatTypeService.getAllSeatTypes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatTypeResponseDTO> getSeatType(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(seatTypeService.getSeatTypeById(id));
    }
}


