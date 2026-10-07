package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model;

import java.util.UUID;

public record ArtistAnalyticsReportModel(
        UUID artistId,
        String artistName,
        String country,
        long totalListens,
        long totalDurationMinutes,
        long uniqueListeners,
        String topTrackTitle,
        String associatedGenres
) {
}