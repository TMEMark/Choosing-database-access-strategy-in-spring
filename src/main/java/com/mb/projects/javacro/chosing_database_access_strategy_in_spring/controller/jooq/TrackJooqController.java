package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.TrackModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.TrackJooqRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks/jooq")
public class TrackJooqController {

    private final TrackJooqRepository trackJooqRepository;

    public TrackJooqController(TrackJooqRepository trackJooqRepository) {
        this.trackJooqRepository = trackJooqRepository;
    }

    /**
     * Endpoint to fetch all tracks using jOOQ.
     */
    @GetMapping
    public List<TrackModel> getAllTracks() {
        return trackJooqRepository.findAll();
    }

    /**
     * Endpoint to fetch a track by its UUID using jOOQ.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrackModel> getTrackById(@PathVariable UUID id) {
        return trackJooqRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new track using jOOQ.
     */
    @PostMapping
    public ResponseEntity<Void> createTrack(@RequestBody TrackModel track) {
        trackJooqRepository.save(track);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to delete a track by its ID using jOOQ.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable UUID id) {
        trackJooqRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
