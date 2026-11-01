package org.example.bookmyshow.movieservice.dto;

import jakarta.validation.constraints.*;
import org.example.bookmyshow.movieservice.entity.Genre;
import org.example.bookmyshow.movieservice.entity.MovieStatus;

import java.time.LocalDate;

public class MovieUpdateDTO {
    @NotBlank
    @Size(max = 200)
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String language;
    @NotNull
    private LocalDate releaseDate;
    @NotNull
    @Min(1)
    private Integer durationMinutes;
    @NotNull
    private Genre genre;
    @NotNull
    private MovieStatus status;

    public MovieUpdateDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }
}
