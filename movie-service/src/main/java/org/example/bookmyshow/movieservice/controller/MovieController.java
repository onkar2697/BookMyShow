package org.example.bookmyshow.movieservice.controller;

import jakarta.validation.*;
import org.example.bookmyshow.movieservice.dto.MovieRequestDTO;
import org.example.bookmyshow.movieservice.dto.MovieResponseDTO;
import org.example.bookmyshow.movieservice.dto.MovieUpdateDTO;
import org.example.bookmyshow.movieservice.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(
            @RequestBody @Valid MovieRequestDTO request) {
        MovieResponseDTO responseDTO = movieService.createMovie(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public Page<MovieResponseDTO> getAllMovies(Pageable pageable) {
        return movieService.findAllByPageable(pageable);
    }

    @GetMapping("/{id}")
    public MovieResponseDTO getMovieByID(@PathVariable long id) {
        return movieService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(
            @PathVariable Long id,
            @RequestBody @Valid MovieUpdateDTO request){

        return ResponseEntity.ok(movieService.updateMovie(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Page<MovieResponseDTO> searchMovies(
            @RequestParam String title,
            Pageable pageable) {

        return movieService.searchByTitle(title, pageable);
    }
}
