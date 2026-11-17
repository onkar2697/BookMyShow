package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Screen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScreenRepository extends JpaRepository<Screen,Long> {
    Page<Screen> findByTheatre_Id(Long theatreId, Pageable pageable);

    List<Screen> findByTheatre_IdAndActiveTrue(Long theatreId);

    boolean existsByNameIgnoreCaseAndTheatre_Id(String name, Long theatreId);
}
