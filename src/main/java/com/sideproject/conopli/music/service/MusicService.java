package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.dto.PageResponseDto;
import com.sideproject.conopli.dto.ResponseDto;
import com.sideproject.conopli.music.dto.MusicDto;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.dto.PopularRequestDto;
import com.sideproject.conopli.repository.TjMusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicService {

    private final TjMusicRepository tjMusicRepository;

    private final CrawlingService crawlingService;

    public PageResponseDto searchMusic(
            MusicNation nation,
            SearchType searchType,
            String keyWord,
            Pageable pageable
    ) {
        Page<MusicQueryDto> queryMusic = tjMusicRepository.findQueryMusic(nation, searchType, keyWord, pageable);
        return PageResponseDto.of(queryMusic.getContent(), queryMusic);
    }

    public ResponseDto searchNewMusic() {
        return crawlingService.getNewMusicCrawling();
    }

    public ResponseDto searchPopularMusic(PopularRequestDto requestDto) {
        return crawlingService.getPopularCrawling(requestDto);
    }


    @Scheduled(cron = "0 0 05 * * *")
    public void saveNewSong() {
        log.info("@@ Start SaveNewSong Task!");
        ResponseDto newMusicCrawling = crawlingService.getNewMusicCrawling();
        List<MusicDto> newMusic = (List<MusicDto>) newMusicCrawling.getData();
        newMusic.forEach(music -> crawlingService.createMusicCrawling(music.getNum(), MusicNation.KOR));
        newMusic.forEach(music -> crawlingService.createMusicCrawling(music.getNum(), MusicNation.JPN));
        newMusic.forEach(music -> crawlingService.createMusicCrawling(music.getNum(), MusicNation.ENG));
        log.info("@@ Finish SaveNewSong Task!");
    }

}
