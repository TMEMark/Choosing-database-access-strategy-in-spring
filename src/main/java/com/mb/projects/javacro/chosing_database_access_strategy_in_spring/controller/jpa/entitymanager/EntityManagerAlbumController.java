package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.entitymanager;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.AlbumEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.entitymanager.EntityManagerAlbumRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums/entity-manager")
public class EntityManagerAlbumController {

    private final EntityManagerAlbumRepository albumRepository;

    public EntityManagerAlbumController(EntityManagerAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    /**
     * Endpoint to fetch all albums using EntityManager JPQL query.
     */
    @GetMapping
    public List<AlbumEntity> getAllAlbums() {
        return albumRepository.findAll(); //
    }

    /**
     * Endpoint to fetch an album by its UUID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlbumEntity> getAlbumById(@PathVariable UUID id) {
        return albumRepository.findById(id) //
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new album using em.persist().
     */
    @PostMapping
    public AlbumEntity createAlbum(@RequestBody AlbumEntity album) {
        return albumRepository.create(album); //
    }

    /**
     * Endpoint to update an album using em.merge().
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlbumEntity> updateAlbum(@PathVariable UUID id, @RequestBody AlbumEntity album) {
        album.setId(id);
        AlbumEntity updated = albumRepository.update(album); //
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete an album by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID id) {
        albumRepository.deleteById(id); //
        return ResponseEntity.noContent().build();
    }
}
