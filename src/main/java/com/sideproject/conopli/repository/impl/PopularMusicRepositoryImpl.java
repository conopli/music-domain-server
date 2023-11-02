package com.sideproject.conopli.repository.impl;

import com.sideproject.conopli.music.entity.PopularMusic;
import com.sideproject.conopli.repository.PopularMusicJpaRepository;
import com.sideproject.conopli.repository.PopularMusicRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PopularMusicRepositoryImpl extends QuerydslRepositorySupport implements PopularMusicRepository {


    public PopularMusicRepositoryImpl(PopularMusicJpaRepository jpaRepository) {
        super(PopularMusic.class);
        this.jpaRepository = jpaRepository;
    }

    private final PopularMusicJpaRepository jpaRepository;

    @Override
    public PopularMusic savePopularMusic(PopularMusic popularMusic) {
        return jpaRepository.save(popularMusic);
    }

    @Override
    public Optional<PopularMusic> findPopularMusic(int yy, int mm, int searchType) {
        return jpaRepository.findByYyAndMmAndSearchType(yy, mm, searchType);
    }
}
