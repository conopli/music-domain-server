package com.sideproject.conopli.repository;

import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.music.entity.KyMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KyMusicRepository {

    KyMusic saveMusic(KyMusic music);

    KyMusic findMusicById(Long kyMusicId);

    KyMusic findQueryMusic(List<String> title, List<String> signer);

    Page<KyMusic> findQueryMusic(SearchType searchType, List<String> keyWords, Pageable pageable);

}
