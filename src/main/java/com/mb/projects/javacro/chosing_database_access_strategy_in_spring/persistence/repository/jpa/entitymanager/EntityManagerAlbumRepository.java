package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.entitymanager;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.AlbumEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EntityManagerAlbumRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public AlbumEntity create(AlbumEntity album) {
        em.persist(album); // Transitions object to Managed state
        return album;
    }

    @Transactional(readOnly = true)
    public Optional<AlbumEntity> findById(UUID id) {
        AlbumEntity album = em.find(AlbumEntity.class, id);
        return Optional.ofNullable(album);
    }

    @Transactional(readOnly = true)
    public List<AlbumEntity> findAll() {
        // Must use JPQL (Java Persistence Query Language), referencing the Entity name, not the table name
        return em.createQuery("SELECT a FROM album a", AlbumEntity.class).getResultList();
    }

    @Transactional
    public AlbumEntity update(AlbumEntity album) {
        // Merge copies the state of the given object onto the persistent object with the same identifier
        return em.merge(album);
    }

    @Transactional
    public void deleteById(UUID id) {
        AlbumEntity album = em.find(AlbumEntity.class, id);
        if (album != null) {
            em.remove(album); // Transitions object to Removed state
        }
    }
}
