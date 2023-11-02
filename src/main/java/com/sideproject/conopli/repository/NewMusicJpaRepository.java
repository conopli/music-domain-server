package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.entity.NewMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewMusicJpaRepository extends JpaRepository<NewMusic, Long> {

    Optional<NewMusic> findByYyAndMm(int yy, int mm);
}
