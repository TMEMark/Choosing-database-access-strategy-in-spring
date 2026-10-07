package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.AlbumModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate.TemplateAlbumRepository;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate.RawJdbcAlbumRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final RawJdbcAlbumRepository rawRepo;
    private final TemplateAlbumRepository templateRepo;

    public AlbumController(RawJdbcAlbumRepository rawRepo, TemplateAlbumRepository templateRepo) {
        this.rawRepo = rawRepo;
        this.templateRepo = templateRepo;
    }

    // --- RAW JDBC ENDPOINTS ---

    @PostMapping("jdbc/raw")
    public ResponseEntity<AlbumModel> createRaw(@RequestBody AlbumModel album) {
        AlbumModel newAlbum = new AlbumModel(
            album.id() != null ? album.id() : UUID.randomUUID(),
            album.title(),
            album.releaseYear(),
            album.artistId()
        );
        rawRepo.create(newAlbum);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAlbum);
    }

    @GetMapping("jdbc/raw/{id}")
    public ResponseEntity<AlbumModel> getByIdRaw(@PathVariable UUID id) {
        AlbumModel album = rawRepo.findById(id);
        return album != null ? ResponseEntity.ok(album) : ResponseEntity.notFound().build();
    }

    @GetMapping("jdbc/raw")
    public ResponseEntity<List<AlbumModel>> getAllRaw() {
        return ResponseEntity.ok(rawRepo.findAll());
    }

    @PutMapping("jdbc/raw/{id}")
    public ResponseEntity<Void> updateRaw(@PathVariable UUID id, @RequestBody AlbumModel album) {
        AlbumModel updated = new AlbumModel(id, album.title(), album.releaseYear(), album.artistId());
        rawRepo.update(updated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("jdbc/raw/{id}")
    public ResponseEntity<Void> deleteRaw(@PathVariable UUID id) {
        rawRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- JDBCTEMPLATE ENDPOINTS ---

    @PostMapping("/template")
    public ResponseEntity<AlbumModel> createTemplate(@RequestBody AlbumModel album) {
        AlbumModel newAlbum = new AlbumModel(
            album.id() != null ? album.id() : UUID.randomUUID(),
            album.title(),
            album.releaseYear(),
            album.artistId()
        );
        templateRepo.create(newAlbum);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAlbum);
    }

    @GetMapping("jdbc/template/{id}")
    public ResponseEntity<AlbumModel> getByIdTemplate(@PathVariable UUID id) {
        AlbumModel album = templateRepo.findById(id);
        return album != null ? ResponseEntity.ok(album) : ResponseEntity.notFound().build();
    }

    @GetMapping("jdbc/template")
    public ResponseEntity<List<AlbumModel>> getAllTemplate() {
        return ResponseEntity.ok(templateRepo.findAll());
    }

    @PutMapping("jdbc/template/{id}")
    public ResponseEntity<Void> updateTemplate(@PathVariable UUID id, @RequestBody AlbumModel album) {
        AlbumModel updated = new AlbumModel(id, album.title(), album.releaseYear(), album.artistId());
        templateRepo.update(updated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("jdbc/template/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}