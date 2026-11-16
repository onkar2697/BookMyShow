package org.example.bookmyshow.theatreservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.TheatreRequestDTO;
import org.example.bookmyshow.theatreservice.dto.TheatreResponseDTO;
import org.example.bookmyshow.theatreservice.dto.TheatreUpdateDTO;
import org.example.bookmyshow.theatreservice.service.TheatreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;


    @PostMapping()
    public ResponseEntity<TheatreResponseDTO> createTheatre
    (@Valid  @RequestBody TheatreRequestDTO theatreRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(theatreService.createTheatre(theatreRequestDTO));
    }

    @GetMapping
    public Page<TheatreResponseDTO> getAllTheatre(Pageable pageable) {
        return theatreService.getAllTheatres(pageable);
    }

    @PutMapping("/{theatreId}")
    public ResponseEntity<TheatreResponseDTO> updateTheatre(
            @PathVariable Long theatreId,
            @Valid @RequestBody TheatreUpdateDTO theatreUpdateDTO) {

        return ResponseEntity.ok(
                theatreService.updateTheatre(theatreId, theatreUpdateDTO)
        );
    }

    @GetMapping("/{theatreId}")
    public ResponseEntity<TheatreResponseDTO> getTheatre(
            @PathVariable Long theatreId) {
        return ResponseEntity.ok(theatreService.getTheatreById(theatreId));
    }

    @PatchMapping("/{theatreId}/deactivate")
    public ResponseEntity<Void> deactivateTheatre(
            @PathVariable Long theatreId) {

        theatreService.deactivateTheatre(theatreId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cities/{cityId}")
    public Page<TheatreResponseDTO> getTheatresByCity(
            @PathVariable Long cityId,
            Pageable pageable) {

        return theatreService.getTheatresByCity(cityId, pageable);
    }

}
