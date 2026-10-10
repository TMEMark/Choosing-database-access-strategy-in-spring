package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.criteriapi;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.AlbumEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.ArtistEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.UserListenHistoryEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistEngagementMetrics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class TemplateAnalyticsCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public TemplateAnalyticsCriteriaRepository() {
    }

    public List<ArtistEngagementMetrics> getArtistEngagementMetricsCriteria(String genre, Integer minReleaseYear) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ArtistEngagementMetrics> query = cb.createQuery(ArtistEngagementMetrics.class);

        Root<TrackEntity> track = query.from(TrackEntity.class);
        Join<TrackEntity, AlbumEntity> album = track.join("album", JoinType.INNER);
        Join<AlbumEntity, ArtistEntity> artist = album.join("artist", JoinType.INNER);

        // Left join listen history (assuming you add a OneToMany relation in TrackEntity,
        // or you can structure it via a root join pattern)
        Join<TrackEntity, UserListenHistoryEntity> history = track.join("listenHistories", JoinType.LEFT);

        // Correlated Subquery for Total Tracks Count per Artist
        Subquery<Long> trackCountSub = query.subquery(Long.class);
        Root<TrackEntity> subTrack = trackCountSub.from(TrackEntity.class);
        Join<TrackEntity, AlbumEntity> subAlbum = subTrack.join("album");
        trackCountSub.select(cb.countDistinct(subTrack.get("id")))
                .where(cb.equal(subAlbum.get("artist"), artist));

        // Dynamic Predicates
        List<Predicate> predicates = new ArrayList<>();
        if (genre != null && !genre.isBlank()) {
            predicates.add(cb.equal(track.get("genreName"), genre));
        }
        if (minReleaseYear != null) {
            predicates.add(cb.ge(album.get("releaseYear"), minReleaseYear));
        }

        query.select(cb.construct(
                ArtistEngagementMetrics.class,
                artist.get("id"),
                artist.get("name"),
                artist.get("country"),
                cb.count(history.get("id")),
                cb.coalesce(cb.sum(cb.quot(track.get("durationSeconds"), 60L)), 0L),
                cb.countDistinct(history.get("userId")),
                trackCountSub
        ));

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.groupBy(artist.get("id"), artist.get("name"), artist.get("country"));
        query.orderBy(cb.desc(cb.count(history.get("id"))));

        return entityManager.createQuery(query).getResultList();
    }
}
