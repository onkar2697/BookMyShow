
package org.example.bookmyshow.theatreservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.SeatRequestDTO;
import org.example.bookmyshow.theatreservice.dto.SeatResponseDTO;
import org.example.bookmyshow.theatreservice.service.SeatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatResponseDTO> createSeat(
            @Valid @RequestBody SeatRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seatService.createSeat(requestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<SeatResponseDTO>> getAllSeats(Pageable pageable) {
        return ResponseEntity.ok(seatService.getAllSeats(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponseDTO> getSeatById(
            @PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<Page<SeatResponseDTO>> getSeatsByScreenId(
            @PathVariable Long screenId,
            Pageable pageable) {
        return ResponseEntity.ok(
                seatService.getSeatsByScreen(screenId,pageable)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDTO> updateSeat(
            @PathVariable Long id,
            @Valid @RequestBody SeatRequestDTO requestDTO) {
        return ResponseEntity.ok(seatService.updateSeat(id, requestDTO));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateSeat(@PathVariable Long id) {
        seatService.deactivateSeat(id);
        return ResponseEntity.noContent().build();
    }
}
