package com.sideproject.conopli.repository;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.entity.TjMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TjMusicRepository {
    TjMusic saveMusic(TjMusic music);

    TjMusic findTjMusicById(Long musicId);

    TjMusic findTjMusicByNum(String num);

    Page<MusicQueryDto> findQueryMusic(MusicNation nation, SearchType searchType, String keyWord, Pageable pageable);
}
