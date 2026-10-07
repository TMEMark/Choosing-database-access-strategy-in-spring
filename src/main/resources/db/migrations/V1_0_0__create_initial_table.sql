CREATE TABLE artist (
                        id UUID PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        country VARCHAR(50) NOT NULL
);

CREATE TABLE album (
                       id UUID PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       release_year INT NOT NULL,
                       artist_id UUID NOT NULL,
                       CONSTRAINT fk_album_artist FOREIGN KEY (artist_id) REFERENCES artist(id)
);

CREATE TABLE track (
                       id UUID PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       duration_seconds INT NOT NULL,
                       genre_name VARCHAR(50) NOT NULL,
                       album_id UUID NOT NULL,
                       CONSTRAINT fk_track_album FOREIGN KEY (album_id) REFERENCES album(id)
);

CREATE TABLE user_listen_history (
                                     id UUID PRIMARY KEY,
                                     user_id UUID NOT NULL,
                                     track_id UUID NOT NULL,
                                     device_type VARCHAR(30) NOT NULL,
                                     listened_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     CONSTRAINT fk_history_track FOREIGN KEY (track_id) REFERENCES track(id)
);

-- Seed initial records
INSERT INTO artist (id, name, country) VALUES ('a1111111-e89b-12d3-a456-426614174000', 'The Weeknd', 'Canada');
INSERT INTO album (id, title, release_year, artist_id) VALUES ('b2222222-e89b-12d3-a456-426614174000', 'After Hours', 2020, 'a1111111-e89b-12d3-a456-426614174000');
INSERT INTO track (id, title, duration_seconds, genre_name, album_id) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Blinding Lights', 200, 'Pop', 'b2222222-e89b-12d3-a456-426614174000');