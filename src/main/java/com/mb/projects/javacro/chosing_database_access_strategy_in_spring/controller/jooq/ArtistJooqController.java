package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.ArtistJooqRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/artists/jooq")
public class ArtistJooqController {

    private final ArtistJooqRepository artistJooqRepository;

    public ArtistJooqController(ArtistJooqRepository artistJooqRepository) {
        this.artistJooqRepository = artistJooqRepository;
    }

    /**
     * Endpoint to fetch all artists using jOOQ.
     */
    @GetMapping
    public List<ArtistModel> getAllArtists() {
        return artistJooqRepository.findAll();
    }

    /**
     * Endpoint to fetch an artist by its UUID using jOOQ.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtistModel> getArtistById(@PathVariable UUID id) {
        return artistJooqRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new artist using jOOQ.
     */
    @PostMapping
    public ResponseEntity<Void> createArtist(@RequestBody ArtistModel artist) {
        artistJooqRepository.save(artist);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to delete an artist by its ID using jOOQ.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable UUID id) {
        artistJooqRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
