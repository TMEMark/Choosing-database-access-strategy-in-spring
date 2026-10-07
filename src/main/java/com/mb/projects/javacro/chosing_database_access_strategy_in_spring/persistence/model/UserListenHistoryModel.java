package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model;

import java.sql.Timestamp;
import java.util.UUID;

public record UserListenHistoryModel(
        UUID id,
        UUID userId,
        UUID trackId,
        String deviceType,
        Timestamp listenedAt
) {}
