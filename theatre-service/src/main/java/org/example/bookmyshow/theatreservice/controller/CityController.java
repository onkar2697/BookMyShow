package org.example.bookmyshow.theatreservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.theatreservice.dto.CityRequestDTO;
import org.example.bookmyshow.theatreservice.dto.CityResponseDTO;
import org.example.bookmyshow.theatreservice.service.CityService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponseDTO> createCity(
            @Valid
            @RequestBody CityRequestDTO request) {
        CityResponseDTO cityResponseDTO = cityService.createCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponseDTO);
    }

    @GetMapping
    public Page<CityResponseDTO> getCity(Pageable pageable) {
        return cityService.getAllCities(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDTO> getCityById(@PathVariable Long id) {
        CityResponseDTO cityResponseDTO = cityService.getCityById(id);
        return ResponseEntity.ok(cityResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDTO> updateCity(
            @PathVariable Long id,
            @Valid @RequestBody CityRequestDTO request) {

        return ResponseEntity.ok(cityService.updateCity(id, request));
    }

}
