package org.example.bookmyshow.movieservice.entity;

import jakarta.validation.constraints.NotBlank;

public class Movie {

    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String releaseDate;
    @NotBlank
    private String language;
    @NotBlank
    private Genre genre;
    @NotBlank
    private MovieStatus status;


}
