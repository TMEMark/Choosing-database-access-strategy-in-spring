package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.controller.jdbc;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.UserListenHistoryModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate.TemplateUserListenHistoryRepository;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate.RawJdbcUserListenHistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
public class UserListenHistoryJdbcController {

    private final RawJdbcUserListenHistoryRepository rawRepo;
    private final TemplateUserListenHistoryRepository templateRepo;

    public UserListenHistoryJdbcController(RawJdbcUserListenHistoryRepository rawRepo, TemplateUserListenHistoryRepository templateRepo) {
        this.rawRepo = rawRepo;
        this.templateRepo = templateRepo;
    }

    // --- RAW JDBC ENDPOINTS ---

    @PostMapping("/raw")
    public ResponseEntity<UserListenHistoryModel> createRaw(@RequestBody UserListenHistoryModel history) {
        UserListenHistoryModel newRecord = new UserListenHistoryModel(
            history.id() != null ? history.id() : UUID.randomUUID(),
            history.userId(),
            history.trackId(),
            history.deviceType(),
            history.listenedAt() != null ? history.listenedAt() : new Timestamp(System.currentTimeMillis())
        );
        rawRepo.create(newRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRecord);
    }

    @GetMapping("/raw/{id}")
    public ResponseEntity<UserListenHistoryModel> getByIdRaw(@PathVariable UUID id) {
        UserListenHistoryModel history = rawRepo.findById(id);
        return history != null ? ResponseEntity.ok(history) : ResponseEntity.notFound().build();
    }

    @GetMapping("/raw")
    public ResponseEntity<List<UserListenHistoryModel>> getAllRaw() {
        return ResponseEntity.ok(rawRepo.findAll());
    }

    @PutMapping("/raw/{id}")
    public ResponseEntity<Void> updateRaw(@PathVariable UUID id, @RequestBody UserListenHistoryModel history) {
        UserListenHistoryModel updated = new UserListenHistoryModel(
            id, history.userId(), history.trackId(), history.deviceType(), history.listenedAt()
        );
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
    public ResponseEntity<UserListenHistoryModel> createTemplate(@RequestBody UserListenHistoryModel history) {
        UserListenHistoryModel newRecord = new UserListenHistoryModel(
            history.id() != null ? history.id() : UUID.randomUUID(),
            history.userId(),
            history.trackId(),
            history.deviceType(),
            history.listenedAt() != null ? history.listenedAt() : new Timestamp(System.currentTimeMillis())
        );
        templateRepo.create(newRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRecord);
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<UserListenHistoryModel> getByIdTemplate(@PathVariable UUID id) {
        UserListenHistoryModel history = templateRepo.findById(id);
        return history != null ? ResponseEntity.ok(history) : ResponseEntity.notFound().build();
    }

    @GetMapping("/template")
    public ResponseEntity<List<UserListenHistoryModel>> getAllTemplate() {
        return ResponseEntity.ok(templateRepo.findAll());
    }

    @PutMapping("/template/{id}")
    public ResponseEntity<Void> updateTemplate(@PathVariable UUID id, @RequestBody UserListenHistoryModel history) {
        UserListenHistoryModel updated = new UserListenHistoryModel(
            id, history.userId(), history.trackId(), history.deviceType(), history.listenedAt()
        );
        templateRepo.update(updated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/template/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}