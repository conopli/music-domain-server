package com.sideproject.conopli.music.controller;


import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.dto.PageResponseDto;
import com.sideproject.conopli.dto.ResponseDto;
import com.sideproject.conopli.music.dto.PopularRequestDto;
import com.sideproject.conopli.music.dto.SearchRequestDto;
import com.sideproject.conopli.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/music")
@Slf4j
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto> searchMusic(
            @ModelAttribute SearchRequestDto requestDto,
            @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC)
            Pageable pageable
            ) {

        PageResponseDto response = musicService.searchMusic(
                MusicNation.valueOf(requestDto.getSearchNation().toUpperCase()),
                SearchType.getSearchTypeName(requestDto.getSearchType()),
                requestDto.getSearchKeyWord(),
                pageable
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/new-music")
    public ResponseEntity<ResponseDto> findNewMusic() {
        ResponseDto response = musicService.searchNewMusic();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/popular")
    public ResponseEntity<ResponseDto> findPopularMusic(
            @ModelAttribute PopularRequestDto requestDto
    ) {
        ResponseDto response = musicService.searchPopularMusic(requestDto);
        return ResponseEntity.ok(response);
    }
}
