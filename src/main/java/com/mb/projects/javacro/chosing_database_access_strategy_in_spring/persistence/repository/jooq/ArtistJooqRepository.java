package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistModel;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.Artist;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ArtistJooqRepository {

    private final DSLContext dsl;

    public ArtistJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<ArtistModel> findAll() {
        Artist a = Artist.ARTIST;
        return dsl.selectFrom(a).fetchInto(ArtistModel.class);
    }

    public Optional<ArtistModel> findById(UUID id) {
        Artist a = Artist.ARTIST;
        return dsl.selectFrom(a)
                .where(a.ID.eq(id))
                .fetchOptionalInto(ArtistModel.class);
    }

    public void save(ArtistModel artist) {
        Artist a = Artist.ARTIST;
        dsl.insertInto(a, a.ID, a.NAME, a.COUNTRY)
                .values(artist.id(), artist.name(), artist.country())
                .onConflict(a.ID)
                .doUpdate()
                .set(a.NAME, artist.name())
                .set(a.COUNTRY, artist.country())
                .execute();
    }

    public void delete(UUID id) {
        Artist a = Artist.ARTIST;
        dsl.deleteFrom(a).where(a.ID.eq(id)).execute();
    }
}