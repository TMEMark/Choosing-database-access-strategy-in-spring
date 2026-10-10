package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.querydsl;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistEngagementMetrics;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.querydsl.TemplateAnalyticsQueryDSLRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/querydsl")
public class TemplateAnalyticsQueryDSLController {

    private final TemplateAnalyticsQueryDSLRepository queryDSLRepository;

    public TemplateAnalyticsQueryDSLController(TemplateAnalyticsQueryDSLRepository queryDSLRepository) {
        this.queryDSLRepository = queryDSLRepository;
    }

    /**
     * Endpoint to fetch artist engagement metrics using QueryDSL.
     * Example: GET /api/analytics/querydsl?genre=Pop&minReleaseYear=2020
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
        return queryDSLRepository.getArtistEngagementMetricsQueryDsl(genre, minReleaseYear);
    }
}
