package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate.TemplateArtistRepository;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate.RawJdbcArtistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final RawJdbcArtistRepository rawRepo;
    private final TemplateArtistRepository templateRepo;

    public ArtistController(RawJdbcArtistRepository rawRepo, TemplateArtistRepository templateRepo) {
        this.rawRepo = rawRepo;
        this.templateRepo = templateRepo;
    }

    // --- RAW JDBC ENDPOINTS ---

    @PostMapping("/raw")
    public ResponseEntity<ArtistModel> createRaw(@RequestBody ArtistModel artist) {
        ArtistModel newArtist = new ArtistModel(
            artist.id() != null ? artist.id() : UUID.randomUUID(),
            artist.name(),
            artist.country()
        );
        rawRepo.create(newArtist);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArtist);
    }

    @GetMapping("/raw/{id}")
    public ResponseEntity<ArtistModel> getByIdRaw(@PathVariable UUID id) {
        ArtistModel artist = rawRepo.findById(id);
        return artist != null ? ResponseEntity.ok(artist) : ResponseEntity.notFound().build();
    }

    @GetMapping("/raw")
    public ResponseEntity<List<ArtistModel>> getAllRaw() {
        return ResponseEntity.ok(rawRepo.findAll());
    }

    @PutMapping("/raw/{id}")
    public ResponseEntity<Void> updateRaw(@PathVariable UUID id, @RequestBody ArtistModel artist) {
        ArtistModel updated = new ArtistModel(id, artist.name(), artist.country());
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
    public ResponseEntity<ArtistModel> createTemplate(@RequestBody ArtistModel artist) {
        ArtistModel newArtist = new ArtistModel(
            artist.id() != null ? artist.id() : UUID.randomUUID(),
            artist.name(),
            artist.country()
        );
        templateRepo.create(newArtist);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArtist);
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<ArtistModel> getByIdTemplate(@PathVariable UUID id) {
        ArtistModel artist = templateRepo.findById(id);
        return artist != null ? ResponseEntity.ok(artist) : ResponseEntity.notFound().build();
    }

    @GetMapping("/template")
    public ResponseEntity<List<ArtistModel>> getAllTemplate() {
        return ResponseEntity.ok(templateRepo.findAll());
    }

    @PutMapping("/template/{id}")
    public ResponseEntity<Void> updateTemplate(@PathVariable UUID id, @RequestBody ArtistModel artist) {
        ArtistModel updated = new ArtistModel(id, artist.name(), artist.country());
        templateRepo.update(updated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/template/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}