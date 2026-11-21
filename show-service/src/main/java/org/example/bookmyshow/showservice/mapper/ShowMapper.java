
package org.example.bookmyshow.showservice.mapper;

import org.example.bookmyshow.showservice.dto.ShowRequest;
import org.example.bookmyshow.showservice.dto.ShowResponse;
import org.example.bookmyshow.showservice.entity.Show;
import org.springframework.stereotype.Component;

@Component
public class ShowMapper {

    public Show toEntity(ShowRequest request) {
        Show show = new Show();
        updateEntity(show, request);
        return show;
    }

    public void updateEntity(Show show, ShowRequest request) {
        show.setMovieId(request.getMovieId());
        show.setScreenId(request.getScreenId());
        show.setScreenTypeId(request.getScreenTypeId());
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setLanguage(request.getLanguage());
        show.setBufferMinutes(
                request.getBufferMinutes() == null ? 15 : request.getBufferMinutes()
        );
    }

    public ShowResponse toResponse(Show show) {
        ShowResponse response = new ShowResponse();

        response.setId(show.getId());
        response.setMovieId(show.getMovieId());
        response.setScreenId(show.getScreenId());
        response.setScreenTypeId(show.getScreenTypeId());
        response.setStartTime(show.getStartTime());
        response.setEndTime(show.getEndTime());
        response.setLanguage(show.getLanguage());
        response.setBufferMinutes(show.getBufferMinutes());
        response.setStatus(show.getStatus().name());
        response.setVersion(show.getVersion());
        response.setCreatedAt(show.getCreatedAt());
        response.setUpdatedAt(show.getUpdatedAt());

        return response;
    }
}
