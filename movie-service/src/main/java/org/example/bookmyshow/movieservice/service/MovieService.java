package org.example.bookmyshow.movieservice.service;


import org.example.bookmyshow.movieservice.dto.MovieRequestDTO;
import org.example.bookmyshow.movieservice.dto.MovieResponseDTO;
import org.example.bookmyshow.movieservice.entity.Movie;
import org.example.bookmyshow.movieservice.mapper.MovieMapper;
import org.example.bookmyshow.movieservice.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public Page<MovieResponseDTO> findAllByPageable(Pageable pageable){

        return null;
    }
    public MovieResponseDTO findById(long id) {
        return null;
    }

    public MovieResponseDTO createMovie(MovieRequestDTO request) {
        return null;
    }

    public MovieResponseDTO updateMovie(long id, MovieRequestDTO request) {
        return null;
    }

    public void deleteMovie(long id) {

    }

    public MovieResponseDTO  searchByTitle(String title) {
        return null;
    }

}
