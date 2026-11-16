package org.example.bookmyshow.theatreservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.ScreenTypeRequestDTO;
import org.example.bookmyshow.theatreservice.dto.ScreenTypeResponseDTO;
import org.example.bookmyshow.theatreservice.service.ScreenTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screen-types")
public class ScreenTypeController {

    private final ScreenTypeService screenTypeService;

    @PostMapping
    public ResponseEntity<ScreenTypeResponseDTO> addScreenType(@Valid  @RequestBody ScreenTypeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(screenTypeService.createScreenType(request));
    }

    @GetMapping
    public ResponseEntity<Page<ScreenTypeResponseDTO>> getAllScreenTypes(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(screenTypeService.getAllScreenTypes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenTypeResponseDTO> getScreenType(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(screenTypeService.getScreenTypeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenTypeResponseDTO> updateScreenType(
            @PathVariable long id,
            @Valid
            @RequestBody ScreenTypeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(screenTypeService.updateScreenType(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateScreenType(@PathVariable Long id){

        screenTypeService.deactivateScreenType(id);
        return ResponseEntity.noContent().build();
    }
}
