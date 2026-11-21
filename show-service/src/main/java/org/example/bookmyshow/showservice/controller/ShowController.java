
package org.example.bookmyshow.showservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.showservice.dto.ShowRequest;
import org.example.bookmyshow.showservice.dto.ShowResponse;
import org.example.bookmyshow.showservice.service.ShowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(
            @Valid @RequestBody ShowRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(showService.createShow(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getShowById(@PathVariable Long id) {

        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ShowResponse>> getAllShows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("startTime").ascending()
        );

        return ResponseEntity.ok(showService.getAllShows(pageable));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<Page<ShowResponse>> getShowsByScreen(
            @PathVariable Long screenId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("startTime").ascending()
        );

        return ResponseEntity.ok(
                showService.getShowsByScreen(screenId, pageable)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowResponse> updateShow(
            @PathVariable Long id,
            @Valid @RequestBody ShowRequest request) {

        return ResponseEntity.ok(showService.updateShow(id, request));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ShowResponse> cancelShow(@PathVariable Long id) {

        return ResponseEntity.ok(showService.cancelShow(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ShowResponse> completeShow(@PathVariable Long id) {

        return ResponseEntity.ok(showService.completeShow(id));
    }
}
