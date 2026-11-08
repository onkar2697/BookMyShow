package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.ScreenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScreenTypeRepository extends JpaRepository<ScreenType,Long> {
    Optional<ScreenType> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}
