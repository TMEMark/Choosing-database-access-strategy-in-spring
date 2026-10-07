package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
public class UserListenHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "listened_at", nullable = false)
    private LocalDateTime listenedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    private TrackEntity track;

    public UserListenHistoryEntity() {
    }

    public UserListenHistoryEntity(UUID userId, TrackEntity track) {
        this.userId = userId;
        this.track = track;
        this.listenedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getListenedAt() {
        return listenedAt;
    }

    public void setListenedAt(LocalDateTime listenedAt) {
        this.listenedAt = listenedAt;
    }

    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(TrackEntity track) {
        this.track = track;
    }
}
