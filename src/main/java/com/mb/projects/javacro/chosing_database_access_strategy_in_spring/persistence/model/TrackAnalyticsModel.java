package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model;

public record TrackAnalyticsModel(
        String artistName,
        String artistCountry,
        String albumTitle,
        String trackTitle,
        String genreName,
        Long totalListens,
        Integer rankInCountry
) {}