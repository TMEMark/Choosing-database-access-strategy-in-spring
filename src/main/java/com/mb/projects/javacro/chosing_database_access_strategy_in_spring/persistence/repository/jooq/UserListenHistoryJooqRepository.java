package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.UserListenHistoryModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.UserListenHistory;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserListenHistoryJooqRepository {

    private final DSLContext dsl;

    public UserListenHistoryJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<UserListenHistoryModel> findAll() {
        UserListenHistory h = UserListenHistory.USER_LISTEN_HISTORY;
        return dsl.selectFrom(h).fetchInto(UserListenHistoryModel.class);
    }

    public Optional<UserListenHistoryModel> findById(UUID id) {
        UserListenHistory h = UserListenHistory.USER_LISTEN_HISTORY;
        return dsl.selectFrom(h)
                .where(h.ID.eq(id))
                .fetchOptionalInto(UserListenHistoryModel.class);
    }

    public void save(UserListenHistoryModel history) {
        UserListenHistory h = UserListenHistory.USER_LISTEN_HISTORY;

        // Convert java.sql.Timestamp to LocalDateTime if it is not null
        var listenedAtLocal = history.listenedAt() != null
                ? history.listenedAt().toLocalDateTime()
                : null;

        dsl.insertInto(h, h.ID, h.USER_ID, h.TRACK_ID, h.DEVICE_TYPE, h.LISTENED_AT)
                .values(history.id(), history.userId(), history.trackId(), history.deviceType(), listenedAtLocal)
                .onConflict(h.ID)
                .doUpdate()
                .set(h.USER_ID, history.userId())
                .set(h.TRACK_ID, history.trackId())
                .set(h.DEVICE_TYPE, history.deviceType())
                .set(h.LISTENED_AT, listenedAtLocal)
                .execute();
    }

    public void delete(UUID id) {
        UserListenHistory h = UserListenHistory.USER_LISTEN_HISTORY;
        dsl.deleteFrom(h).where(h.ID.eq(id)).execute();
    }
}