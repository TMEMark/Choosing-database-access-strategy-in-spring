package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistEngagementMetrics;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.ArtistAnalyticsJooqRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/jooq")
public class ArtistAnalyticsController {

    private final ArtistAnalyticsJooqRepository artistAnalyticsJooqRepository;

    public ArtistAnalyticsController(ArtistAnalyticsJooqRepository artistAnalyticsJooqRepository) {
        this.artistAnalyticsJooqRepository = artistAnalyticsJooqRepository;
    }


    /**
     * Endpoint to fetch artist engagement metrics using jOOQ.
     * Example: GET /api/analytics/jooq?genre=Pop&minReleaseYear=2020
     *
     * @param genre          Optional genre filter
     * @param minReleaseYear Optional minimum release year filter
     * @return List of {@link ArtistEngagementMetrics}
     */
    @GetMapping
    public List<ArtistEngagementMetrics> getArtistEngagementMetrics(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer minReleaseYear
    ) {
        return artistAnalyticsJooqRepository.getArtistEngagementMetrics(genre, minReleaseYear);
    }
}
