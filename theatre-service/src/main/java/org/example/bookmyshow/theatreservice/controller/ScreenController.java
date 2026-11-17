package org.example.bookmyshow.theatreservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.ScreenRequestDTO;
import org.example.bookmyshow.theatreservice.dto.ScreenResponseDTO;
import org.example.bookmyshow.theatreservice.repository.ScreenRepository;
import org.example.bookmyshow.theatreservice.service.ScreenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screens")
@RequiredArgsConstructor
public class ScreenController {
    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ScreenResponseDTO> createScreen(@Valid @RequestBody ScreenRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(screenService.createScreen(request));
    }
    
    @GetMapping
    public ResponseEntity<Page<ScreenResponseDTO>> getAllScreens(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(screenService.getAllScreens(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponseDTO> getScreenById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(screenService.getScreenById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenResponseDTO> updateScreen(@PathVariable Long id, @Valid @RequestBody ScreenRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(screenService.updateScreen(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void>  deactivateScreen(@PathVariable Long id) {
        screenService.deactivateScreen(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
