package com.sideproject.conopli.music.controller;


import com.sideproject.conopli.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/music")
@Slf4j
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;


}
