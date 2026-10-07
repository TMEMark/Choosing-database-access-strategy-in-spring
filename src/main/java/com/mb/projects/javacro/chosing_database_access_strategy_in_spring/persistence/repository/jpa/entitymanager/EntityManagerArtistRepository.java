package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.entitymanager;


import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.ArtistEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EntityManagerArtistRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ArtistEntity create(ArtistEntity artist) {
        em.persist(artist); // Transitions object to Managed state
        return artist;
    }

    @Transactional(readOnly = true)
    public Optional<ArtistEntity> findById(UUID id) {
        ArtistEntity artist = em.find(ArtistEntity.class, id);
        return Optional.ofNullable(artist);
    }

    @Transactional(readOnly = true)
    public List<ArtistEntity> findAll() {
        // Must use JPQL (Java Persistence Query Language), referencing the Entity name, not the table name
        return em.createQuery("SELECT a FROM Artist a", ArtistEntity.class).getResultList();
    }

    @Transactional
    public ArtistEntity update(ArtistEntity artist) {
        // Merge copies the state of the given object onto the persistent object with the same identifier
        return em.merge(artist);
    }

    @Transactional
    public void deleteById(UUID id) {
        ArtistEntity artist = em.find(ArtistEntity.class, id);
        if (artist != null) {
            em.remove(artist); // Transitions object to Removed state
        }
    }
}
