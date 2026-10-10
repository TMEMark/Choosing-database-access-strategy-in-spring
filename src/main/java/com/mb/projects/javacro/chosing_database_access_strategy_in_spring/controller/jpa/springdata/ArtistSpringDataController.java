package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jpa.springdata;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.ArtistEntity;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.springdata.SpringDataArtistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/artists/spring-data")
public class ArtistSpringDataController {

    private final SpringDataArtistRepository artistRepository;

    public ArtistSpringDataController(SpringDataArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * Endpoint to fetch all artists using Spring Data JPA findAll().
     */
    @GetMapping
    public List<ArtistEntity> getAllArtists() {
        return artistRepository.findAll();
    }

    /**
     * Endpoint to fetch an artist by its UUID using Spring Data JPA findById().
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtistEntity> getArtistById(@PathVariable UUID id) {
        return artistRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to find an artist by name using the custom Spring Data query method.
     * Example: GET /api/artists/spring-data/search?name=The%20Weeknd
     */
    @GetMapping("/search")
    public ResponseEntity<ArtistEntity> getArtistByName(@RequestParam String name) {
        return artistRepository.findArtistByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new artist using Spring Data JPA save().
     */
    @PostMapping
    public ArtistEntity createArtist(@RequestBody ArtistEntity artist) {
        return artistRepository.save(artist);
    }

    /**
     * Endpoint to update an existing artist using Spring Data JPA save().
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtistEntity> updateArtist(@PathVariable UUID id, @RequestBody ArtistEntity artist) {
        if (!artistRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artist.setId(id);
        ArtistEntity updated = artistRepository.save(artist);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete an artist by its ID using Spring Data JPA deleteById().
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable UUID id) {
        if (!artistRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artistRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
