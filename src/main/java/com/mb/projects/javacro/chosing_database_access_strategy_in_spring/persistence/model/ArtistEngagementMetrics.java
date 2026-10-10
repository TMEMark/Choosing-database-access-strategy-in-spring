package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model;

import java.util.UUID;

public class ArtistEngagementMetrics {
    private UUID artistId;
    private String artistName;
    private String country;
    private Long totalListens;
    private Long totalDurationMinutes;
    private Long uniqueListeners;
    private Long totalTracksCount;

    public ArtistEngagementMetrics(
            UUID artistId,
            String artistName,
            String country,
            Long totalListens,
            Long totalDurationMinutes,
            Long uniqueListeners,
            Long totalTracksCount
    ) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.country = country;
        this.totalListens = totalListens;
        this.totalDurationMinutes = totalDurationMinutes;
        this.uniqueListeners = uniqueListeners;
        this.totalTracksCount = totalTracksCount;
    }

    // Getters
    public UUID getArtistId() { return artistId; }
    public String getArtistName() { return artistName; }
    public String getCountry() { return country; }
    public Long getTotalListens() { return totalListens; }
    public Long getTotalDurationMinutes() { return totalDurationMinutes; }
    public Long getUniqueListeners() { return uniqueListeners; }
    public Long getTotalTracksCount() { return totalTracksCount; }
}