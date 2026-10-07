package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.TrackModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate.TemplateTrackRepository;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate.RawJdbcTrackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final RawJdbcTrackRepository rawRepo;
    private final TemplateTrackRepository templateRepo;

    public TrackController(RawJdbcTrackRepository rawRepo, TemplateTrackRepository templateRepo) {
        this.rawRepo = rawRepo;
        this.templateRepo = templateRepo;
    }

    // --- RAW JDBC ENDPOINTS ---

    @PostMapping("/raw")
    public ResponseEntity<TrackModel> createRaw(@RequestBody TrackModel track) {
        TrackModel newTrack = new TrackModel(
            track.id() != null ? track.id() : UUID.randomUUID(),
            track.title(),
            track.durationSeconds(),
            track.genreName(),
            track.albumId()
        );
        rawRepo.create(newTrack);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTrack);
    }

    @GetMapping("/raw/{id}")
    public ResponseEntity<TrackModel> getByIdRaw(@PathVariable UUID id) {
        TrackModel track = rawRepo.findById(id);
        return track != null ? ResponseEntity.ok(track) : ResponseEntity.notFound().build();
    }

    @GetMapping("/raw")
    public ResponseEntity<List<TrackModel>> getAllRaw() {
        return ResponseEntity.ok(rawRepo.findAll());
    }

    @PutMapping("/raw/{id}")
    public ResponseEntity<Void> updateRaw(@PathVariable UUID id, @RequestBody TrackModel track) {
        TrackModel updated = new TrackModel(id, track.title(), track.durationSeconds(), track.genreName(), track.albumId());
        rawRepo.update(updated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/raw/{id}")
    public ResponseEntity<Void> deleteRaw(@PathVariable UUID id) {
        rawRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- JDBCTEMPLATE ENDPOINTS ---

    @PostMapping("/template")
    public ResponseEntity<TrackModel> createTemplate(@RequestBody TrackModel track) {
        TrackModel newTrack = new TrackModel(
            track.id() != null ? track.id() : UUID.randomUUID(),
            track.title(),
            track.durationSeconds(),
            track.genreName(),
            track.albumId()
        );
        templateRepo.create(newTrack);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTrack);
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<TrackModel> getByIdTemplate(@PathVariable UUID id) {
        TrackModel track = templateRepo.findById(id);
        return track != null ? ResponseEntity.ok(track) : ResponseEntity.notFound().build();
    }

    @GetMapping("/template")
    public ResponseEntity<List<TrackModel>> getAllTemplate() {
        return ResponseEntity.ok(templateRepo.findAll());
    }

    @PutMapping("/template/{id}")
    public ResponseEntity<Void> updateTemplate(@PathVariable UUID id, @RequestBody TrackModel track) {
        TrackModel updated = new TrackModel(id, track.title(), track.durationSeconds(), track.genreName(), track.albumId());
        templateRepo.update(updated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/template/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}