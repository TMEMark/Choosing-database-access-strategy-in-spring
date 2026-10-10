package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.entitymanager;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.ArtistEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.entitymanager.EntityManagerArtistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/artists/entity-manager")
public class EntityManagerArtistController {

    private final EntityManagerArtistRepository artistRepository;

    public EntityManagerArtistController(EntityManagerArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * Endpoint to fetch all artists using EntityManager JPQL query.
     */
    @GetMapping
    public List<ArtistEntity> getAllArtists() {
        return artistRepository.findAll();
    }

    /**
     * Endpoint to fetch an artist by its UUID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtistEntity> getArtistById(@PathVariable UUID id) {
        return artistRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new artist using em.persist().
     */
    @PostMapping
    public ArtistEntity createArtist(@RequestBody ArtistEntity artist) {
        return artistRepository.create(artist);
    }

    /**
     * Endpoint to update an artist using em.merge().
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtistEntity> updateArtist(@PathVariable UUID id, @RequestBody ArtistEntity artist) {
        artist.setId(id);
        ArtistEntity updated = artistRepository.update(artist);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete an artist by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable UUID id) {
        artistRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
