package org.example.bookmyshow.movieservice.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.bookmyshow.movieservice.entity.Genre;

import java.time.LocalDate;

public class MovieRequestDTO {

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private Integer durationMinutes;

    @NotBlank
    private String language;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private Genre genre;

    public MovieRequestDTO() {
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

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
