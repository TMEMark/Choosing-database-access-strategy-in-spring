package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.springdata;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.TrackEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.springdata.SpringDataTrackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks/spring-data")
public class TrackSpringDataController {

    private final SpringDataTrackRepository trackRepository;

    public TrackSpringDataController(SpringDataTrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /**
     * Endpoint to fetch all tracks using Spring Data JPA findAll().
     */
    @GetMapping
    public List<TrackEntity> getAllTracks() {
        return trackRepository.findAll();
    }

    /**
     * Endpoint to fetch a track by its UUID using Spring Data JPA findById().
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrackEntity> getTrackById(@PathVariable UUID id) {
        return trackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to filter tracks by genre and minimum duration using Spring Data derived query.
     * Example: GET /api/tracks/spring-data/filter?genre=Pop&minDuration=180
     */
    @GetMapping("/filter")
    public List<TrackEntity> findLongTracksByGenre(
            @RequestParam String genre,
            @RequestParam Integer minDuration
    ) {
        return trackRepository.findByGenreNameAndDurationSecondsGreaterThanOrderByTitleAsc(genre, minDuration);
    }

    /**
     * Endpoint to create a new track using Spring Data JPA save().
     */
    @PostMapping
    public TrackEntity createTrack(@RequestBody TrackEntity track) {
        return trackRepository.save(track);
    }

    /**
     * Endpoint to update an existing track using Spring Data JPA save().
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrackEntity> updateTrack(@PathVariable UUID id, @RequestBody TrackEntity track) {
        if (!trackRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        track.setId(id);
        TrackEntity updated = trackRepository.save(track);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete a track by its ID using Spring Data JPA deleteById().
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable UUID id) {
        if (!trackRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        trackRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
