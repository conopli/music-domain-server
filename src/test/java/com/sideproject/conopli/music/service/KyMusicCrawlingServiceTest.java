package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.repository.TjMusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
class KyMusicCrawlingServiceTest {

    @Autowired
    KyMusicCrawlingService service;

    @Autowired
    TjMusicRepository repository;

    @Test
    @DisplayName("String Array Search And TjMusic Matching")
    void sample() {
        String s1 = "사랑이 멈춘 시간....... (Song By 민경훈)";
        String s2 = "사랑은 가슴이 시킨다 ....Part.3";
        String s3 = "can't control my self";
        String s4 = "사랑은..가슴이 시킨다";
        String s5 = "꿈을 찾아서 (게임\"브리스톨탐험대\")";
        String s6= "i can't stop me";
        // 괄호 안 문자 제거
        String bracket = s3.replaceAll("\\([^)]*\\)", "");
        // 특수문자 공백으로 변경
        String characters = bracket.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " ");

        String[] s00 = characters.split(" ");
        List<String> list = Arrays.stream(s00).filter(str -> (!str.isEmpty())).toList();
        PageRequest of = PageRequest.of(0, 10);
        Page<MusicQueryDto> queryMusic = repository.findQueryMusic(
                MusicNation.KOR,
                SearchType.TITLE,
                list,
                of
        );
        assertThat(queryMusic.getContent().get(0).getTitle()).isEqualTo("Can't Control Myself");

    }


}