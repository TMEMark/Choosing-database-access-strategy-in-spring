package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistEngagementMetrics;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.Album;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.Artist;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.Track;
import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jooq.tables.UserListenHistory;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistAnalyticsJooqRepository {
    private final DSLContext dsl;

    public ArtistAnalyticsJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<ArtistEngagementMetrics> getArtistEngagementMetrics(String genre, Integer minReleaseYear) {
        // References to jOOQ generated table classes
        Artist a = Artist.ARTIST;
        Album al = Album.ALBUM;
        Track t = Track.TRACK;
        UserListenHistory h = UserListenHistory.USER_LISTEN_HISTORY;

        // Aliased tables for the correlated subquery
        Album al2 = Album.ALBUM.as("al2");
        Track t2 = Track.TRACK.as("t2");

        // Dynamic filtering conditions
        Condition condition = DSL.noCondition();
        if (genre != null && !genre.isBlank()) {
            condition = condition.and(t.GENRE_NAME.eq(genre));
        }
        if (minReleaseYear != null) {
            condition = condition.and(al.RELEASE_YEAR.ge(minReleaseYear));
        }

        return dsl
                .select(
                        a.ID,
                        a.NAME,
                        a.COUNTRY,
                        DSL.count(h.ID),
                        DSL.coalesce(DSL.sum(t.DURATION_SECONDS).cast(Long.class).div(60), 0L),
                        DSL.countDistinct(h.USER_ID),
                        // Wrapped scalar subquery for total tracks count per artist
                        DSL.field(
                                DSL.select(DSL.countDistinct(t2.ID))
                                        .from(al2)
                                        .join(t2).on(al2.ID.eq(t2.ALBUM_ID))
                                        .where(al2.ARTIST_ID.eq(a.ID))
                        )
                )
                .from(a)
                .join(al).on(a.ID.eq(al.ARTIST_ID))
                .join(t).on(al.ID.eq(t.ALBUM_ID))
                .leftJoin(h).on(t.ID.eq(h.TRACK_ID))
                .where(condition)
                .groupBy(a.ID, a.NAME, a.COUNTRY)
                .orderBy(DSL.count(h.ID).desc())
                .fetchInto(ArtistEngagementMetrics.class);
    }
}
