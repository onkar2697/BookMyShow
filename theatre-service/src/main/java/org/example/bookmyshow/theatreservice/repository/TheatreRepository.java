package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    List<Theatre> findByCity_Id(Long cityId);

    List<Theatre> findByCity_IdAndActiveTrue(Long cityId);

    boolean existsByNameIgnoreCaseAndCity_Id(String name, Long cityId);
}
