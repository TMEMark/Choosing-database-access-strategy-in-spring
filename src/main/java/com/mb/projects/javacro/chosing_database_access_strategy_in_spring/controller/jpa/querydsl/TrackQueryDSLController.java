package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.querydsl;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.querydsl.TrackQueryDSLRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tracks/querydsl")
public class TrackQueryDSLController {

    private final TrackQueryDSLRepository trackQueryDslRepository;

    public TrackQueryDSLController(TrackQueryDSLRepository trackQueryDslRepository) {
        this.trackQueryDslRepository = trackQueryDslRepository;
    }

    /**
     * Endpoint to filter track entries by genre and minimum duration using QueryDSL.
     * Example: GET /api/tracks/querydsl?genre=Pop&minDuration=180
     *
     * @param genre       Name of the genre
     * @param minDuration Minimum duration of the track
     * @return List containing {@link TrackEntity}
     */
    @GetMapping
    public List<TrackEntity> findLongTracksByGenre(
            @RequestParam String genre,
            @RequestParam Integer minDuration
    ) {
        return trackQueryDslRepository.findLongTracksByGenre(genre, minDuration);
    }
}
