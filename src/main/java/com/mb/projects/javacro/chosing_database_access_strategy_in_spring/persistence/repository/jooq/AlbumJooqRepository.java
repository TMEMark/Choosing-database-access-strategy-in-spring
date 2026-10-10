package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.AlbumModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.Album;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AlbumJooqRepository {

    private final DSLContext dsl;

    public AlbumJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<AlbumModel> findAll() {
        Album al = Album.ALBUM;
        return dsl.selectFrom(al).fetchInto(AlbumModel.class);
    }

    public Optional<AlbumModel> findById(UUID id) {
        Album al = Album.ALBUM;
        return dsl.selectFrom(al)
                .where(al.ID.eq(id))
                .fetchOptionalInto(AlbumModel.class);
    }

    public void save(AlbumModel album) {
        Album al = Album.ALBUM;
        dsl.insertInto(al, al.ID, al.TITLE, al.RELEASE_YEAR, al.ARTIST_ID)
                .values(album.id(), album.title(), album.releaseYear(), album.artistId())
                .onConflict(al.ID)
                .doUpdate()
                .set(al.TITLE, album.title())
                .set(al.RELEASE_YEAR, album.releaseYear())
                .set(al.ARTIST_ID, album.artistId())
                .execute();
    }

    public void delete(UUID id) {
        Album al = Album.ALBUM;
        dsl.deleteFrom(al).where(al.ID.eq(id)).execute();
    }
}