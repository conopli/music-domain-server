package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.entity.TjMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TjMusicJpaRepository extends JpaRepository<TjMusic, Long> {
    Optional<TjMusic> findByNum(String num);
}
