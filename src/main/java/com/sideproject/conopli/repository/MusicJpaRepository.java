package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicJpaRepository extends JpaRepository<Music, Long> {
}
