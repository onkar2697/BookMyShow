package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre,Long> {
}
