package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.entity.PopularMusic;

import java.util.Optional;

public interface PopularMusicRepository {

    PopularMusic savePopularMusic(PopularMusic popularMusic);

    Optional<PopularMusic> findPopularMusic(int yy, int mm, int searchType);
}
