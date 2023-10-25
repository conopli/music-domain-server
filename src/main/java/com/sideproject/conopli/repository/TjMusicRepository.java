package com.sideproject.conopli.repository;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.entity.TjMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TjMusicRepository {
    TjMusic saveMusic(TjMusic music);

    TjMusic findTjMusicById(Long musicId);

    TjMusic findTjMusicByNum(String num);

    Page<MusicQueryDto> findQueryMusic(MusicNation nation, SearchType searchType, List<String> keyWord, Pageable pageable);

    Page<MusicQueryDto> findQueryMusic(SearchType searchType, List<String> keyWords, Pageable pageable);

    Page<MusicQueryDto> findQueryMusicByYyMm(int yy, int mm, Pageable pageable);

    TjMusic findQueryMusic(List<String> title, List<String> signer);

}
