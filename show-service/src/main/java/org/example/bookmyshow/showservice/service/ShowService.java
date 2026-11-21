
package org.example.bookmyshow.showservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookmyshow.showservice.dto.ShowRequest;
import org.example.bookmyshow.showservice.dto.ShowResponse;
import org.example.bookmyshow.showservice.entity.Show;
import org.example.bookmyshow.showservice.entity.ShowStatus;
import org.example.bookmyshow.showservice.exception.ResourceNotFoundException;
import org.example.bookmyshow.showservice.mapper.ShowMapper;
import org.example.bookmyshow.showservice.repository.ShowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowMapper showMapper;

    @Transactional
    public ShowResponse createShow(ShowRequest request) {

        validateShowTimes(request.getStartTime(), request.getEndTime());

        validateScreenSchedule(
                request.getScreenId(),
                request.getStartTime(),
                request.getEndTime(),
                request.getBufferMinutes() == null ? 15 : request.getBufferMinutes(),
                null
        );

        Show show = showMapper.toEntity(request);
        Show savedShow = showRepository.save(show);

        return showMapper.toResponse(savedShow);
    }

    @Transactional(readOnly = true)
    public ShowResponse getShowById(Long id) {

        Show show = getShowEntity(id);
        return showMapper.toResponse(show);
    }

    @Transactional(readOnly = true)
    public Page<ShowResponse> getAllShows(Pageable pageable) {

        return showRepository.findAll(pageable)
                .map(showMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ShowResponse> getShowsByScreen(Long screenId, Pageable pageable) {

        return showRepository.findAll(pageable)
                .map(showMapper::toResponse)
                .map(response -> response);
    }

    @Transactional
    public ShowResponse updateShow(Long id, ShowRequest request) {

        Show existingShow = getShowEntity(id);

        if (existingShow.getStatus() != ShowStatus.SCHEDULED) {
            throw new IllegalArgumentException(
                    "Only scheduled shows can be updated"
            );
        }

        validateShowTimes(request.getStartTime(), request.getEndTime());

        validateScreenSchedule(
                request.getScreenId(),
                request.getStartTime(),
                request.getEndTime(),
                request.getBufferMinutes() == null ? 15 : request.getBufferMinutes(),
                id
        );

        showMapper.updateEntity(existingShow, request);

        Show updatedShow = showRepository.save(existingShow);

        return showMapper.toResponse(updatedShow);
    }

    @Transactional
    public ShowResponse cancelShow(Long id) {

        Show show = getShowEntity(id);

        if (show.getStatus() != ShowStatus.SCHEDULED) {
            throw new IllegalArgumentException(
                    "Only scheduled shows can be cancelled"
            );
        }

        show.setStatus(ShowStatus.CANCELLED);

        return showMapper.toResponse(showRepository.save(show));
    }

    @Transactional
    public ShowResponse completeShow(Long id) {

        Show show = getShowEntity(id);

        if (show.getStatus() != ShowStatus.SCHEDULED) {
            throw new IllegalArgumentException(
                    "Only scheduled shows can be marked completed"
            );
        }

        show.setStatus(ShowStatus.COMPLETED);

        return showMapper.toResponse(showRepository.save(show));
    }

    private Show getShowEntity(Long id) {

        return showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found with id: " + id)
                );
    }

    private void validateShowTimes(LocalDateTime startTime, LocalDateTime endTime) {

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "Show end time must be after start time"
            );
        }
    }

    private void validateScreenSchedule(
            Long screenId,
            LocalDateTime newStart,
            LocalDateTime newEnd,
            Integer newBuffer,
            Long excludeShowId
    ) {

        var scheduledShows = showRepository.findByScreenIdAndStatus(
                screenId,
                ShowStatus.SCHEDULED
        );

        for (Show existing : scheduledShows) {

            if (excludeShowId != null && existing.getId().equals(excludeShowId)) {
                continue;
            }

            LocalDateTime existingEndWithBuffer =
                    existing.getEndTime().plusMinutes(existing.getBufferMinutes());

            LocalDateTime newEndWithBuffer =
                    newEnd.plusMinutes(newBuffer);

            boolean conflict =
                    newStart.isBefore(existingEndWithBuffer)
                            && existing.getStartTime().isBefore(newEndWithBuffer);

            if (conflict) {
                throw new IllegalArgumentException(
                        "Screen " + screenId +
                                " already has a show scheduled during this time or buffer period"
                );
            }
        }
    }
}
