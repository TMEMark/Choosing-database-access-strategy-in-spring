package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jpa.springdata;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataAlbumRepository extends JpaRepository<AlbumEntity, UUID> {
}
