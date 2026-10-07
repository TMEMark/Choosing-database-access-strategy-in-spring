package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model;

import java.util.UUID;

public record ArtistModel(
        UUID id,
        String name,
        String country
) {
}
