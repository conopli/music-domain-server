package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.dto.PageResponseDto;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;



@SpringBootTest
class MusicServiceTest {

    @Autowired
    private MusicService service;

    @Test
    void givenSearchParameterWhenSearchMusicThenContainsTitle() {
        // Given
        Pageable pageable = PageRequest.of(1, 10);
        MusicNation nation = MusicNation.KOR;
        SearchType searchType = SearchType.TITLE;
        String keyWord = "아리랑";
        // When
        PageResponseDto pageResponseDto = service.searchMusic(nation, searchType, keyWord, pageable);
        List<MusicQueryDto> data = (List<MusicQueryDto>) pageResponseDto.getData();
        String title = data.get(0).getTitle();
        // Then
        Assertions.assertThat(title.contains(keyWord)).isTrue();

    }

}