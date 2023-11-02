package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.entity.PopularMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopMusicEntityJpaRepository extends JpaRepository<PopularMusicEntity, Long> {
}
