package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "album")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistEntity artist;

    public AlbumEntity() {
    }

    public AlbumEntity(String title, Integer releaseYear, ArtistEntity artist) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.artist = artist;
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }
}
