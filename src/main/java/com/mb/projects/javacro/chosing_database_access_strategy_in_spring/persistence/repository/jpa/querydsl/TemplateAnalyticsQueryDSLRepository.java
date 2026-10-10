package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.querydsl;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.QAlbumEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.QArtistEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.QTrackEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.QUserListenHistoryEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistEngagementMetrics;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TemplateAnalyticsQueryDSLRepository {

    private final JPAQueryFactory queryFactory;

    public TemplateAnalyticsQueryDSLRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<ArtistEngagementMetrics> getArtistEngagementMetricsQueryDsl(String genre, Integer minReleaseYear) {
        QArtistEntity artist = QArtistEntity.artistEntity;
        QAlbumEntity album = QAlbumEntity.albumEntity;
        QTrackEntity track = QTrackEntity.trackEntity;
        QUserListenHistoryEntity history = QUserListenHistoryEntity.userListenHistoryEntity;

        QAlbumEntity subAlbum = new QAlbumEntity("subAlbum");
        QTrackEntity subTrack = new QTrackEntity("subTrack");

        BooleanBuilder builder = new BooleanBuilder();
        if (genre != null && !genre.isBlank()) {
            builder.and(track.genreName.eq(genre));
        }
        if (minReleaseYear != null) {
            builder.and(album.releaseYear.goe(minReleaseYear));
        }

        return queryFactory
                .select(Projections.constructor(
                        ArtistEngagementMetrics.class,
                        artist.id,
                        artist.name,
                        artist.country,
                        history.id.count(),
                        track.durationSeconds.sum().divide(60).coalesce(0).longValue(),
                        history.userId.countDistinct(),
                        JPAExpressions.select(subTrack.id.countDistinct())
                                .from(subTrack)
                                .join(subTrack.album, subAlbum)
                                .where(subAlbum.artist.eq(artist))
                ))
                .from(track)
                .join(track.album, album)
                .join(album.artist, artist)
                .leftJoin(history).on(history.track.eq(track))
                .where(builder)
                .groupBy(artist.id, artist.name, artist.country)
                .orderBy(history.id.count().desc())
                .fetch();
    }
}
