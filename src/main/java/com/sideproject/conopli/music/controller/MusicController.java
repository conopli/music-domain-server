package com.sideproject.conopli.music.controller;


import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.dto.PageResponseDto;
import com.sideproject.conopli.dto.ResponseDto;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.dto.PopularMusicResponseDto;
import com.sideproject.conopli.music.dto.SearchRequestDto;
import com.sideproject.conopli.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
            @PageableDefault(page = 0, size = 20, sort = "num", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {

        Page<MusicQueryDto> queryMusic = musicService.searchMusic(
                SearchType.getSearchTypeName(requestDto.getSearchType()),
                requestDto.getSearchKeyWord(),
                pageable
        );

        return ResponseEntity.ok(
                PageResponseDto.of(queryMusic.getContent(), queryMusic)
        );
    }

    @GetMapping("/search/{musicNum}")
    public ResponseEntity<?> searchMusicByNum(
            @PathVariable String musicNum
    ) {
        MusicQueryDto musicQueryDto = musicService.searchMusicByNum(musicNum);
        return ResponseEntity.ok(ResponseDto.of(musicQueryDto));
    }

    @GetMapping("/new-music")
    public ResponseEntity<PageResponseDto> findNewMusic(
            @RequestParam String yy,
            @RequestParam String mm,
            @PageableDefault(page = 0, size = 20)
            Pageable pageable
    ) {
        Page<MusicQueryDto> findNewMusic = musicService.searchNewMusic(yy, mm, pageable);
        return ResponseEntity.ok(PageResponseDto.of(findNewMusic.getContent(), findNewMusic));
    }

    @GetMapping("/popular")
    public ResponseEntity<PageResponseDto> findPopularMusic(
            @RequestParam String yy,
            @RequestParam String mm,
            @RequestParam String searchType,
            @PageableDefault(page = 0, size = 20, sort = "ranking", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<PopularMusicResponseDto> response = musicService.searchPopularMusic(yy, mm, searchType, pageable);
        return ResponseEntity.ok(PageResponseDto.of(response.getContent(), response));
    }
}
