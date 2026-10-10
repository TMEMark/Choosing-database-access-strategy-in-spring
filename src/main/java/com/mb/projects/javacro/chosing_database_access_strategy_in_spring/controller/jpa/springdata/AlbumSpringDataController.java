package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.springdata;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.AlbumEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.springdata.SpringDataAlbumRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums/spring-data")
public class AlbumSpringDataController {

    private final SpringDataAlbumRepository albumRepository;

    public AlbumSpringDataController(SpringDataAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    /**
     * Endpoint to fetch all albums using Spring Data JPA findAll().
     */
    @GetMapping
    public List<AlbumEntity> getAllAlbums() {
        return albumRepository.findAll();
    }

    /**
     * Endpoint to fetch an album by its UUID using Spring Data JPA findById().
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlbumEntity> getAlbumById(@PathVariable UUID id) {
        return albumRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new album using Spring Data JPA save().
     */
    @PostMapping
    public AlbumEntity createAlbum(@RequestBody AlbumEntity album) {
        return albumRepository.save(album);
    }

    /**
     * Endpoint to update an existing album using Spring Data JPA save().
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlbumEntity> updateAlbum(@PathVariable UUID id, @RequestBody AlbumEntity album) {
        if (!albumRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        album.setId(id);
        AlbumEntity updated = albumRepository.save(album);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete an album by its ID using Spring Data JPA deleteById().
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID id) {
        if (!albumRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        albumRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
