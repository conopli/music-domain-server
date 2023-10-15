package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.exception.ServiceLogicException;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.entity.KyMusic;
import com.sideproject.conopli.repository.KyMusicRepository;
import com.sideproject.conopli.repository.TjMusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
class ServiceTest {

    @Autowired
    KyMusicCrawlingService service;

    @Autowired
    MusicService musicService;

    @Autowired
    TjMusicRepository tjMusicRepository;

    @Autowired
    KyMusicRepository kyMusicRepository;

    @Test
    @DisplayName("String Array Search And TjMusic Matching")
    @Disabled
    void sample() {
        String s1 = "사랑이 멈춘 시간....... (Song By 민경훈)";
        String s2 = "사랑은 가슴이 시킨다 ....Part.3";
        String s3 = "can't control my self";
        String s4 = "사랑은..가슴이 시킨다";
        String s5 = "꿈을 찾아서 (게임\"브리스톨탐험대\")";
        String s6= "i can't stop me";
        // 괄호 안 문자 제거
        String bracket = s4.replaceAll("\\([^)]*\\)", "");
        // 특수문자 공백으로 변경
        String characters = bracket.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " ");

        String[] s00 = characters.split(" ");
        List<String> list = Arrays.stream(s00).filter(str -> (!str.isEmpty())).toList();
        PageRequest of = PageRequest.of(0, 10);
        Page<MusicQueryDto> queryMusic = tjMusicRepository.findQueryMusic(
                MusicNation.KOR,
                SearchType.TITLE,
                list,
                of
        );
        for (MusicQueryDto musicQueryDto : queryMusic) {
            System.out.println(musicQueryDto.getTitle());
            System.out.println(musicQueryDto.getSinger());
        }

    }

    private List<String> filteringString(String keyWord) {
        // 괄호 안 문자 제거
        String bracket = keyWord.replaceAll("\\([^)]*\\)", "");
        // 특수문자 공백으로 변경
        String characters = bracket.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " ");
        String[] s00 = characters.split(" ");
        return Arrays.stream(s00).filter(str -> (!str.isEmpty())).toList();
    }

    @Test
    @DisplayName("TjMusic Matching Logic")
    @Disabled
    void mapping() {
        List<Long> list = new ArrayList<>();
        for (int i = 1; i < 100000; i++) {
            list.add((long) i);
        }
        List<String> musicList = new ArrayList<>();
        list.stream().forEach(a -> {
            try {
                Long l = musicService.updateTjMusicByTjMusicId(a);
                if (l != null) {
                    musicList.add(String.valueOf(l));
                }
            } catch (ServiceLogicException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        for (String mappingMusic : musicList) {
            System.out.println(mappingMusic);
        }
        System.out.println("Matching Size = "+musicList.size());

    }
    @Test
    @DisplayName("KeyWord KyMusic 검색 후 TJ 반영 로직")
    @Disabled
    void updateKeyWordKyMusic() {
        String string = "Wanna One";
        List<String> singer = filteringString(string);
        PageRequest page = PageRequest.of(0, 1000);
        Page<KyMusic> findMusicPage = kyMusicRepository.findQueryMusic(SearchType.SINGER, singer, page);
        findMusicPage.map(m -> m.getMusicId())
                .forEach(id -> musicService.updateTjMusicByKyMusicId(id));

    }

    @Test
    @DisplayName("KeyWord TjMusic 검색 후 TJ 반영 로직")
    @Disabled
    void updateKeyWordKyMusicByKyTitle() {
        String string = "iu";
        List<String> singer = filteringTjMusicString(string);
        PageRequest page = PageRequest.of(0, 1000);
        Page<MusicQueryDto> findMusicPage = tjMusicRepository.findQueryMusic(SearchType.SINGER, singer, page);
        findMusicPage.map(m -> m.getMusicId())
                .forEach(id -> musicService.updateTjMusicByTjMusicId(id));
    }

    private List<String> filteringTjMusicString(String tjMusicTitle) {
        // 괄호 안 문자 제거
        String bracket = tjMusicTitle.replaceAll("\\([^)]*\\)", "");
        // 특수문자 공백으로 변경
        String characters = bracket.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " ");
        String[] s00 = characters.split("");
        return Arrays.stream(s00).filter(str -> (!str.isEmpty())).toList();
    }
}