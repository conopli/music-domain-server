package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.dto.ResponseDto;
import com.sideproject.conopli.music.dto.MusicDto;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.dto.PopularRequestDto;
import com.sideproject.conopli.music.entity.KyMusic;
import com.sideproject.conopli.music.entity.TjMusic;
import com.sideproject.conopli.repository.KyMusicRepository;
import com.sideproject.conopli.repository.TjMusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicService {

    private final TjMusicRepository tjMusicRepository;

    private final TjMusicCrawlingService tjMusicCrawlingService;

    private final KyMusicCrawlingService kyMusicCrawlingService;

    private final KyMusicRepository kyMusicRepository;

    public Page<MusicQueryDto> searchMusic(
            MusicNation nation,
            SearchType searchType,
            String keyWord,
            Pageable pageable
    ) {
        Pageable customPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.Direction.DESC,
                searchType.name().toLowerCase(),
                "title"
        );
        return tjMusicRepository.findQueryMusic(nation, searchType, filteringDetailSplitString(keyWord), customPageable);
    }

    public MusicQueryDto searchMusicByNum(String musicNum) {
        TjMusic tjMusicByNum = tjMusicRepository.findTjMusicByNum(musicNum);
        return MusicQueryDto.of(tjMusicByNum);
    }

    public ResponseDto searchNewMusic(String yy, String mm) {
        return tjMusicCrawlingService.getNewMusicCrawling(yy, mm);
    }

    public ResponseDto searchPopularMusic(PopularRequestDto requestDto) {
        return tjMusicCrawlingService.getPopularCrawling(requestDto);
    }

    public Long updateTjMusicByKyMusicId(Long kyMusicId) {
        KyMusic findKyMusic = kyMusicRepository.findMusicById(kyMusicId);
        String kyTitle = findKyMusic.getTitle();
        List<String> title = filteringString(kyTitle);
        String kySinger = findKyMusic.getSinger();
        List<String> singer = filteringString(kySinger);
        TjMusic findTjMusic = tjMusicRepository.findQueryMusic(title, singer);
        if (findTjMusic != null) {
            findTjMusic.setKyNum(findKyMusic.getNum());
            findKyMusic.setTjMappedCheck(true);
            return kyMusicId;
        } else {
            return null;
        }
    }

    public Long updateTjMusicByTjMusicId(Long tjMusicId) {
        TjMusic findTjMusic = tjMusicRepository.findTjMusicById(tjMusicId);
        return mappingKyMusicByFindTjMusic(findTjMusic);
    }

    public Long updateTjMusicByTjMusicNum(String num) {
        TjMusic findTjMusic = tjMusicRepository.findTjMusicByNum(num);
        return mappingKyMusicByFindTjMusic(findTjMusic);
    }

    private Long mappingKyMusicByFindTjMusic(TjMusic findTjMusic) {
        String tjTitle = findTjMusic.getTitle();
        String tjSinger = findTjMusic.getSinger();
        List<String> titleStrings = filteringDetailSplitString(tjTitle);
        List<String> singerStrings = filteringDetailSplitString(tjSinger);
        KyMusic findKyMusic = kyMusicRepository.findQueryMusic(titleStrings, singerStrings);
        if (findKyMusic != null) {
            findTjMusic.setKyNum(findKyMusic.getNum());
            findKyMusic.setTjMappedCheck(true);
            return findKyMusic.getMusicId();
        } else {
            return null;
        }
    }

    private List<String> filteringString(String keyWord) {
        // 괄호 안 문자 제거
        String bracket = keyWord.replaceAll("\\([^)]*\\)", "");
        // 특수문자 공백으로 변경
        String characters = bracket.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " ");
        String[] s00 = characters.split(" ");
        return Arrays.stream(s00).filter(str -> (!str.isEmpty() && !str.equals(" "))).toList();
    }

    private List<String> filteringDetailSplitString(String keyWord) {
        // 괄호 안 문자 제거
        String bracket = keyWord.replaceAll("\\([^)]*\\)", "");
        // 특수문자 공백으로 변경
        String characters = bracket.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " ");
        String[] s00 = characters.split("");
        return Arrays.stream(s00).filter(str -> (!str.isEmpty() && !str.equals(" "))).toList();

    }


    @Scheduled(cron = "0 0 05 * * *")
    public void saveNewSong() {
        log.info("@@ Start SaveNewSong Task!");
        ResponseDto newMusicCrawling = tjMusicCrawlingService.getNewMusicCrawling(
                String.valueOf(LocalDateTime.now().getYear()),
                String.format("%02d", LocalDateTime.now().getMonthValue())
        );
        List<KyMusic> kyMusics = kyMusicCrawlingService.kyNewMusicCrawling();
        List<MusicDto> newMusic = (List<MusicDto>) newMusicCrawling.getData();
        newMusic.forEach(music -> tjMusicCrawlingService.createMusicCrawling(music.getNum(), MusicNation.KOR));
        newMusic.forEach(music -> tjMusicCrawlingService.createMusicCrawling(music.getNum(), MusicNation.JPN));
        newMusic.forEach(music -> tjMusicCrawlingService.createMusicCrawling(music.getNum(), MusicNation.ENG));
        newMusic.forEach(musicDto -> {
            try {
                updateTjMusicByTjMusicNum(musicDto.getNum());
            } catch (Exception e) {
                log.info("### CHINA MUSIC "+ musicDto.getNum());
            }
        });
        kyMusics.forEach(kyMusic -> updateTjMusicByKyMusicId(kyMusic.getMusicId()));
        log.info("@@ Finish SaveNewSong Task!");
    }

}
