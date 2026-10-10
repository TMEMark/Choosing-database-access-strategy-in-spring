package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.entitymanager;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.entitymanager.EntityManagerTrackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks/entity-manager")
public class EntityManagerTrackController {

    private final EntityManagerTrackRepository trackRepository;

    public EntityManagerTrackController(EntityManagerTrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /**
     * Endpoint to fetch all tracks using EntityManager JPQL query.
     */
    @GetMapping
    public List<TrackEntity> getAllTracks() {
        return trackRepository.findAll();
    }

    /**
     * Endpoint to fetch a track by its UUID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrackEntity> getTrackById(@PathVariable UUID id) {
        return trackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to filter tracks by genre and minimum duration.
     * Example: GET /api/tracks/entity-manager/filter?genre=Pop&minDuration=180
     */
    @GetMapping("/filter")
    public List<TrackEntity> findLongTracksByGenre(
            @RequestParam String genre,
            @RequestParam Integer minDuration
    ) {
        return trackRepository.findLongTracksByGenre(genre, minDuration);
    }

    /**
     * Endpoint to create a new track using em.persist().
     */
    @PostMapping
    public TrackEntity createTrack(@RequestBody TrackEntity track) {
        return trackRepository.create(track);
    }

    /**
     * Endpoint to update a track using em.merge().
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrackEntity> updateTrack(@PathVariable UUID id, @RequestBody TrackEntity track) {
        track.setId(id);
        TrackEntity updated = trackRepository.update(track);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete a track by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable UUID id) {
        trackRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
