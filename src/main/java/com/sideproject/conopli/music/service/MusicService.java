package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.ErrorCode;
import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.dto.ResponseDto;
import com.sideproject.conopli.exception.ServiceLogicException;
import com.sideproject.conopli.music.dto.*;
import com.sideproject.conopli.music.entity.*;
import com.sideproject.conopli.repository.*;
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
import java.util.LinkedHashSet;
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

    private final NewMusicRepository newMusicRepository;

    private final PopularMusicRepository popularMusicRepository;

    private final PopMusicEntityRepository popMusicEntityRepository;

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

    public Page<MusicQueryDto> searchNewMusic(String yy, String mm, Pageable pageable) {
        return tjMusicRepository.findNewMusicByYyMm(Integer.parseInt(yy), Integer.parseInt(mm), pageable);
    }

    public Page<PopularMusicResponseDto> searchPopularMusic(String yy, String mm, String searchType, Pageable pageable) {
        return popMusicEntityRepository.findPopularMusic(Integer.parseInt(yy), Integer.parseInt(mm), Integer.parseInt(searchType), pageable)
                .map(PopularMusicResponseDto::of);
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

    public void savePopularMusic(int yy, int mm, int searchType) {
        PopularRequestDto request = PopularRequestDto.builder()
                .searchType(String.valueOf(searchType))
                .syy(String.valueOf(yy))
                .eyy(String.valueOf(yy))
                .emm(String.format("%02d", mm))
                .smm(String.format("%02d", mm))
                .build();
        PopularMusic popularMusic = PopularMusic.builder()
                .yy(yy)
                .mm(mm)
                .searchType(searchType)
                .musics(new LinkedHashSet<>())
                .build();
        PopularMusic savePopularMusic = popularMusicRepository.savePopularMusic(popularMusic);

    }

    public void savePopularMusicEntity(int yy, int mm, int searchType) {
        PopularRequestDto request = PopularRequestDto.builder()
                .searchType(String.valueOf(searchType))
                .syy(String.valueOf(yy))
                .eyy(String.valueOf(yy))
                .emm(String.format("%02d", mm))
                .smm(String.format("%02d", mm))
                .build();
        PopularMusic findPopularMusic = popularMusicRepository.findPopularMusic(yy, mm, searchType)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND_MUSIC));
        ResponseDto popularCrawling = tjMusicCrawlingService.getPopularCrawling(request);
        for (PopularResponseDto music : (List<PopularResponseDto>) popularCrawling.getData()) {
            TjMusic findTjMusic = tjMusicRepository.findTjMusicByNum(music.getNum());
            PopularMusicEntity popularMusicEntity = PopularMusicEntity.of(findTjMusic, music.getRanking());
            popularMusicEntity.addPopularMusic(findPopularMusic);
            popularMusicRepository.savePopularMusic(findPopularMusic);
        }
    }


    @Scheduled(cron = "0 0 05 * * *")
    public void saveNewSong() {
        log.info("@@ Start SaveNewSong Task!");
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        ResponseDto newMusicCrawling = tjMusicCrawlingService.getNewMusicCrawling(
                String.valueOf(year),
                String.format("%02d", month)
        );
        List<MusicDto> newMusicList = (List<MusicDto>) newMusicCrawling.getData();
        for (MusicNation musicNation : Arrays.asList(MusicNation.KOR, MusicNation.JPN, MusicNation.ENG)) {
            newMusicList.forEach(music -> {
                log.info("### # New TjMusic MUSIC = {}", music.getNum());
                tjMusicCrawlingService.createMusicCrawling(music.getNum(), musicNation);
            });
        }
        List<KyMusic> kyMusics = kyMusicCrawlingService.kyNewMusicCrawling();
        updateKyNumForNewMusic(newMusicList, kyMusics);
        log.info("@@ Finish SaveNewSong Task!");
    }

    @Scheduled(cron = "0 0 06 * * *")
    public void updateNewMusicEntity() {
        log.info("@@ Start UpdateNewMusicEntity Task!");
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        ResponseDto newMusicCrawling = tjMusicCrawlingService.getNewMusicCrawling(
                String.valueOf(year),
                String.format("%02d", month)
        );
        NewMusic findNewMusic = newMusicRepository.findNewMusic(year, month)
                .orElse(
                        NewMusic.builder()
                                .yy(year)
                                .mm(month)
                                .musics(new LinkedHashSet<>())
                                .build()
                );

        List<MusicDto> newMusicList = (List<MusicDto>) newMusicCrawling.getData();
        saveNewTjMusic(newMusicList, findNewMusic);
        log.info("@@ Finish UpdateNewMusicEntity Task!");

    }

    private void saveNewTjMusic(List<MusicDto> newMusicList, NewMusic findNewMusic) {
        newMusicList.forEach(music -> {
            try {
                tjMusicRepository.findTjMusicByNum(music.getNum()).addNewMusic(findNewMusic);
            } catch (Exception e) {
                log.info("### # CHINA MUSIC = {}", music.getNum());
                log.info("### # Error Message = {}", e.getMessage());
            }
            newMusicRepository.saveNewMusic(findNewMusic);
        });
    }

    private void updateKyNumForNewMusic(List<MusicDto> newMusicList, List<KyMusic> kyMusics) {
        newMusicList.forEach(musicDto -> {
            try {
                updateTjMusicByTjMusicNum(musicDto.getNum());
            } catch (Exception e) {
                log.info("### Error Message = {}", e.getMessage());
                log.info("### Error MUSIC = {}", musicDto.getNum());
            }
        });
        kyMusics.forEach(kyMusic -> updateTjMusicByKyMusicId(kyMusic.getMusicId()));
    }


}
