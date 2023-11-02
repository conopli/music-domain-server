package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.entity.NewMusic;
import com.sideproject.conopli.music.entity.PopularMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PopularMusicJpaRepository extends JpaRepository<PopularMusic, Long> {

    Optional<PopularMusic> findByYyAndMmAndSearchType(int yy, int mm, int searchType);

}
