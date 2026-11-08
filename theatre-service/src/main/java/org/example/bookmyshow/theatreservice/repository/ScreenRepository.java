package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScreenRepository extends JpaRepository<Screen,Long> {
    List<Screen> findByTheatre_Id(Long theatreId);

    List<Screen> findByTheatre_IdAndActiveTrue(Long theatreId);

    boolean existsByNameIgnoreCaseAndTheatre_Id(String name, Long theatreId);
}
