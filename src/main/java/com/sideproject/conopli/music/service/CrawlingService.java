package com.sideproject.conopli.music.service;


import com.sideproject.conopli.repository.MusicJpaRepository;
import com.sideproject.conopli.repository.MusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingService {

    private final MusicRepository musicRepository;

}
