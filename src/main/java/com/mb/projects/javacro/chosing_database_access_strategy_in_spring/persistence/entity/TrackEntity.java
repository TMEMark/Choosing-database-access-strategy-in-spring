package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "track")
public class TrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "genre_name")
    private String genreName;

    // Explicitly set to LAZY to avoid pulling the Album and Artist automatically
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

    public TrackEntity() {
    }

    public TrackEntity(String title, Integer durationSeconds, String genreName, AlbumEntity album) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.genreName = genreName;
        this.album = album;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }
}
