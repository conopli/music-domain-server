package com.sideproject.conopli.repository.impl;

import com.sideproject.conopli.music.entity.KyMusic;
import com.sideproject.conopli.music.entity.NewMusic;
import com.sideproject.conopli.repository.NewMusicJpaRepository;
import com.sideproject.conopli.repository.NewMusicRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NewMusicRepositoryImpl extends QuerydslRepositorySupport implements NewMusicRepository {

    public NewMusicRepositoryImpl(NewMusicJpaRepository jpaRepository) {
        super(KyMusic.class);
        this.jpaRepository = jpaRepository;
    }

    private final NewMusicJpaRepository jpaRepository;


    @Override
    public NewMusic saveNewMusic(NewMusic newMusic) {
        return jpaRepository.save(newMusic);
    }


    @Override
    public Optional<NewMusic> findNewMusic(int yy, int mm) {
        return jpaRepository.findByYyAndMm(yy, mm);
    }
}
