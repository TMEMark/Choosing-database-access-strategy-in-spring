package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.criteriaapi;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistEngagementMetrics;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.criteriapi.TemplateAnalyticsCriteriaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/criteria")
public class TemplateAnalyticsCriteriaApiController {

    private final TemplateAnalyticsCriteriaRepository criteriaRepository;

    public TemplateAnalyticsCriteriaApiController(TemplateAnalyticsCriteriaRepository criteriaRepository) {
        this.criteriaRepository = criteriaRepository;
    }

    /**
     * Endpoint to fetch artist engagement metrics using JPA Criteria API.
     * Example: GET /api/analytics/criteria?genre=Pop&minReleaseYear=2020
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
        return criteriaRepository.getArtistEngagementMetricsCriteria(genre, minReleaseYear);
    }
}
