package com.sideproject.conopli.repository.impl;

import com.sideproject.conopli.music.entity.KyMusic;
import com.sideproject.conopli.repository.KyMusicJpaRepository;
import com.sideproject.conopli.repository.KyMusicRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KyMusicRepositoryImpl implements KyMusicRepository {

    private final KyMusicJpaRepository jpaRepository;

    private final EntityManager em;
    @Override
    public KyMusic saveMusic(KyMusic music) {
        Optional<KyMusic> findMusic = jpaRepository.findFirstByNum(music.getNum());
        if (findMusic.isPresent()) {
            return null;
        } else {
            return jpaRepository.save(music);
        }
    }
}
