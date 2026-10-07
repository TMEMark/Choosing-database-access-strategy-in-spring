package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.criteriapi;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TrackCriteriaRepository {

    @PersistenceContext
    private EntityManager em;

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
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TrackEntity> query = cb.createQuery(TrackEntity.class);
        Root<TrackEntity> track = query.from(TrackEntity.class);

        query.select(track)
                .where(
                        cb.equal(track.get("genreName"), genre),
                        cb.greaterThan(track.get("durationSeconds"), minDuration)
                )
                .orderBy(cb.asc(track.get("title")));

        TypedQuery<TrackEntity> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }
}