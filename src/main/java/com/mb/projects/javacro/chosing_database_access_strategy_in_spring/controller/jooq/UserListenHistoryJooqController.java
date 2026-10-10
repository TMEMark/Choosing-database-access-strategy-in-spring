package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.UserListenHistoryModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.UserListenHistoryJooqRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/listen-history/jooq")
public class UserListenHistoryJooqController {

    private final UserListenHistoryJooqRepository listenHistoryJooqRepository;

    public UserListenHistoryJooqController(UserListenHistoryJooqRepository listenHistoryJooqRepository) {
        this.listenHistoryJooqRepository = listenHistoryJooqRepository;
    }

    /**
     * Endpoint to fetch all user listen history records using jOOQ.
     */
    @GetMapping
    public List<UserListenHistoryModel> getAllListenHistory() {
        return listenHistoryJooqRepository.findAll();
    }

    /**
     * Endpoint to fetch a user listen history record by its UUID using jOOQ.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserListenHistoryModel> getListenHistoryById(@PathVariable UUID id) {
        return listenHistoryJooqRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new user listen history record using jOOQ.
     */
    @PostMapping
    public ResponseEntity<Void> createListenHistory(@RequestBody UserListenHistoryModel listenHistory) {
        listenHistoryJooqRepository.save(listenHistory);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to delete a user listen history record by its ID using jOOQ.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListenHistory(@PathVariable UUID id) {
        listenHistoryJooqRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
