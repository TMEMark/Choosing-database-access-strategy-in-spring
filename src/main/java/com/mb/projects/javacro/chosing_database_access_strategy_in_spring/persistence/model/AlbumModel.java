package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model;

import java.util.UUID;

public record AlbumModel(
        UUID id,
        String title,
        Integer releaseYear,
        UUID artistId
) {
}
