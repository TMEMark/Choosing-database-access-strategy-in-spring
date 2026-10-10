package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.AlbumModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.AlbumJooqRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums/jooq")
public class AlbumJooqController {

    private final AlbumJooqRepository albumJooqRepository;

    public AlbumJooqController(AlbumJooqRepository albumJooqRepository) {
        this.albumJooqRepository = albumJooqRepository;
    }

    /**
     * Endpoint to fetch all albums using jOOQ.
     */
    @GetMapping
    public List<AlbumModel> getAllAlbums() {
        return albumJooqRepository.findAll();
    }

    /**
     * Endpoint to fetch an album by its UUID using jOOQ.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlbumModel> getAlbumById(@PathVariable UUID id) {
        return albumJooqRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new album using jOOQ.
     */
    @PostMapping
    public ResponseEntity<Void> createAlbum(@RequestBody AlbumModel album) {
        albumJooqRepository.save(album);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to delete an album by its ID using jOOQ.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID id) {
        albumJooqRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
