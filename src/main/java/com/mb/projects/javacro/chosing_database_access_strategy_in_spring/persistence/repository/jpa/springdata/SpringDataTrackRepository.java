package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.springdata;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataTrackRepository extends JpaRepository<TrackEntity, UUID> {

    /**
     * Filters Track entries by genre and minimum duration.
     * Tracks are ordered by title ascending.
     *
     * @param genre name of the genre
     * @param minDuration minimum duration of the track
     * @return List containing {@link TrackEntity}
     */
    List<TrackEntity> findByGenreNameAndDurationSecondsGreaterThanOrderByTitleAsc(
            String genre,
            Integer minDuration
    );
}
