
package org.example.bookmyshow.theatreservice.repository;

import org.example.bookmyshow.theatreservice.entity.Theatre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    List<Theatre> findByCity_Id(Long cityId);

    List<Theatre> findByCity_IdAndActiveTrue(Long cityId);

    Page<Theatre> findByCity_Id(Long cityId, Pageable pageable);

    boolean existsByNameIgnoreCaseAndCity_Id(String name, Long cityId);

    Optional<Theatre> findByNameIgnoreCaseAndCity_Id(
            String name, Long cityId
    );
}
