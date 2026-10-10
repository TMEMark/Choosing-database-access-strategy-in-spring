package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.querydsl;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.QTrackEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TrackQueryDSLRepository {

    private final JPAQueryFactory queryFactory;

    public TrackQueryDSLRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * Filters Track entries by genre and minimum duration.
     * Tracks are ordered by title ascending.
     *
     * @param genre name of the genre
     * @param minDuration minimum duration of the track
     * @return List containing {@link TrackEntity}
     */
    @Transactional(readOnly = true)
    public List<TrackEntity> findLongTracksByGenre(String genre, Integer minDuration) {
        QTrackEntity track = QTrackEntity.trackEntity;

        return queryFactory
                .selectFrom(track)
                .where(
                        track.genreName.eq(genre),
                        track.durationSeconds.gt(minDuration)
                )
                .orderBy(track.title.asc())
                .fetch();
    }
}