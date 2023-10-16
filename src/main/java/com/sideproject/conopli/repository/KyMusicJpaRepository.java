package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.entity.KyMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KyMusicJpaRepository extends JpaRepository<KyMusic, Long> {

    Optional<KyMusic> findFirstByNum(String num);
}
