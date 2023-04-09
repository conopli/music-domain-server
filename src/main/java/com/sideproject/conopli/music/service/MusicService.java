package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.dto.PageResponseDto;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.repository.TjMusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final TjMusicRepository tjMusicRepository;

    public PageResponseDto searchMusic(
            MusicNation nation,
            SearchType searchType,
            String keyWord,
            Pageable pageable
    ) {
        Page<MusicQueryDto> queryMusic = tjMusicRepository.findQueryMusic(nation, searchType, keyWord, pageable);
        return PageResponseDto.of(queryMusic.getContent(), queryMusic);
    }

}
