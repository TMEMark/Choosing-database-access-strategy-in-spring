package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.TrackModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.Track;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TrackJooqRepository {

    private final DSLContext dsl;

    public TrackJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<TrackModel> findAll() {
        Track t = Track.TRACK;
        return dsl.selectFrom(t).fetchInto(TrackModel.class);
    }

    public Optional<TrackModel> findById(UUID id) {
        Track t = Track.TRACK;
        return dsl.selectFrom(t)
                .where(t.ID.eq(id))
                .fetchOptionalInto(TrackModel.class);
    }

    public void save(TrackModel track) {
        Track t = Track.TRACK;
        dsl.insertInto(t, t.ID, t.TITLE, t.DURATION_SECONDS, t.GENRE_NAME, t.ALBUM_ID)
                .values(track.id(), track.title(), track.durationSeconds(), track.genreName(), track.albumId())
                .onConflict(t.ID)
                .doUpdate()
                .set(t.TITLE, track.title())
                .set(t.DURATION_SECONDS, track.durationSeconds())
                .set(t.GENRE_NAME, track.genreName())
                .set(t.ALBUM_ID, track.albumId())
                .execute();
    }

    public void delete(UUID id) {
        Track t = Track.TRACK;
        dsl.deleteFrom(t).where(t.ID.eq(id)).execute();
    }
}