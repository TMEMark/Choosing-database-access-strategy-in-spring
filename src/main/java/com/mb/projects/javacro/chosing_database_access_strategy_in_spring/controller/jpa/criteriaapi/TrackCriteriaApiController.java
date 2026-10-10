package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.criteriaapi;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.criteriapi.TrackCriteriaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tracks/criteria")
public class TrackCriteriaApiController {

    private final TrackCriteriaRepository trackCriteriaRepository;

    public TrackCriteriaApiController(TrackCriteriaRepository trackCriteriaRepository) {
        this.trackCriteriaRepository = trackCriteriaRepository;
    }

    /**
     * Endpoint to filter track entries by genre and minimum duration using JPA Criteria API.
     * Example: GET /api/tracks/criteria?genre=Pop&minDuration=180
     *
     * @param genre       Name of the genre
     * @param minDuration Minimum duration of the track in seconds
     * @return List containing {@link TrackEntity}
     */
    @GetMapping
    public List<TrackEntity> findLongTracksByGenre(
            @RequestParam String genre,
            @RequestParam Integer minDuration
    ) {
        return trackCriteriaRepository.findLongTracksByGenre(genre, minDuration);
    }
}
