package org.example.bookmyshow.movieservice.service;


import org.example.bookmyshow.movieservice.dto.MovieRequestDTO;
import org.example.bookmyshow.movieservice.dto.MovieResponseDTO;
import org.example.bookmyshow.movieservice.dto.MovieUpdateDTO;
import org.example.bookmyshow.movieservice.entity.Movie;
import org.example.bookmyshow.movieservice.exception.MovieNotFoundException;
import org.example.bookmyshow.movieservice.mapper.MovieMapper;
import org.example.bookmyshow.movieservice.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {

        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Transactional(readOnly = true)
    public Page<MovieResponseDTO> findAllByPageable(Pageable pageable){

        return movieRepository.findAll(pageable).map(movieMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public MovieResponseDTO findById(long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new  MovieNotFoundException(String.format("Movie with id %d not found", id)));
        return movieMapper.toDTO(movie);
    }

    @Transactional
    public MovieResponseDTO createMovie(MovieRequestDTO request) {

        Movie movie = movieMapper.toEntity(request);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toDTO(savedMovie);
    }

    @Transactional
    public MovieResponseDTO updateMovie(long id, MovieUpdateDTO request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException(String.format("Movie with id %d not found", id)));
        movieMapper.updateEntity(movie, request);
        Movie updatedMovie = movieRepository.save(movie);
        return movieMapper.toDTO(updatedMovie);

    }

    @Transactional
    public void deleteMovie(long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException(String.format("Movie with id %d not found", id)));

        movieRepository.delete(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieResponseDTO> searchByTitle(String title, Pageable pageable) {

        return movieRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(movieMapper::toDTO);
    }
}
