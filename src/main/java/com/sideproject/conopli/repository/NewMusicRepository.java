package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.dto.NewMusicDto;
import com.sideproject.conopli.music.entity.NewMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NewMusicRepository {

    NewMusic saveNewMusic(NewMusic newMusic);

    Optional<NewMusic> findNewMusic(int yy, int mm);
}
