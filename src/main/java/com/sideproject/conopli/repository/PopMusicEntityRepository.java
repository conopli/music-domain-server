package com.sideproject.conopli.repository;

import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.entity.PopularMusicEntity;
import com.sideproject.conopli.music.entity.TjMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

public interface PopMusicEntityRepository {

    Page<PopularMusicEntity> findPopularMusic(int yy, int mm, int searchType, Pageable pageable);

    PopularMusicEntity savePopularEntityMusic(PopularMusicEntity music);


}
