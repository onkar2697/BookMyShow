package org.example.bookmyshow.movieservice.mapper;


import org.example.bookmyshow.movieservice.dto.MovieRequestDTO;
import org.example.bookmyshow.movieservice.dto.MovieResponseDTO;
import org.example.bookmyshow.movieservice.dto.MovieUpdateDTO;
import org.example.bookmyshow.movieservice.entity.Movie;
import org.springframework.stereotype.Component;


@Component
public class MovieMapper {

    public Movie toEntity(MovieRequestDTO request) {
        Movie movie = new Movie();

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDurationMinutes(request.getDurationMinutes());

        return movie;
    }

    public MovieResponseDTO toDTO(Movie entity) {
        MovieResponseDTO dto = new MovieResponseDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setGenre(entity.getGenre());
        dto.setLanguage(entity.getLanguage());
        dto.setDurationMinutes(entity.getDurationMinutes());
        dto.setStatus(entity.getStatus());
        dto.setRating(entity.getRating());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public void updateEntity(Movie movie, MovieUpdateDTO request){
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setStatus(request.getStatus());
    }


}
