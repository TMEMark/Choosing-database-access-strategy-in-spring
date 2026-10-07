package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.entitymanager;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EntityManagerTrackRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public TrackEntity create(TrackEntity track) {
        em.persist(track); // Transitions object to Managed state
        return track;
    }

    @Transactional(readOnly = true)
    public Optional<TrackEntity> findById(UUID id) {
        TrackEntity track = em.find(TrackEntity.class, id);
        return Optional.ofNullable(track);
    }

    @Transactional(readOnly = true)
    public List<TrackEntity> findAll() {
        // Must use JPQL (Java Persistence Query Language), referencing the Entity name, not the table name
        return em.createQuery("SELECT a FROM track a", TrackEntity.class).getResultList();
    }

    @Transactional
    public TrackEntity update(TrackEntity track) {
        // Merge copies the state of the given object onto the persistent object with the same identifier
        return em.merge(track);
    }

    @Transactional
    public void deleteById(UUID id) {
        TrackEntity track = em.find(TrackEntity.class, id);
        if (track != null) {
            em.remove(track); // Transitions object to Removed state
        }
    }

    // TODO -> @marko.buljan ubaci find by name

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
        String jpql = """
                SELECT t FROM TrackEntity t
                WHERE t.genreName = :genre
                  AND t.durationSeconds > :minDuration
                ORDER BY t.title ASC
                """;

        return em.createQuery(jpql, TrackEntity.class)
                .setParameter("genre", genre)
                .setParameter("minDuration", minDuration)
                .getResultList();
    }
}
