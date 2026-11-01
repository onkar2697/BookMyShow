package org.example.bookmyshow.movieservice.repository;

import org.example.bookmyshow.movieservice.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie,Long> {

    Page<Movie> findTitleContainingIgnoreCase(String title, Pageable pageable);

}
