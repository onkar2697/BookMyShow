
package org.example.bookmyshow.showservice.repository;

import org.example.bookmyshow.showservice.entity.Show;
import org.example.bookmyshow.showservice.entity.ShowStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByScreenIdAndStatus(Long screenId, ShowStatus status);

    Page<Show> findByScreenId(Long screenId, Pageable pageable);
}
