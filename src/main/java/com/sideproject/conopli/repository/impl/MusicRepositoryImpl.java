package com.sideproject.conopli.repository.impl;

import com.sideproject.conopli.constant.ErrorCode;
import com.sideproject.conopli.exception.ServiceLogicException;
import com.sideproject.conopli.music.entity.Music;
import com.sideproject.conopli.repository.MusicJpaRepository;
import com.sideproject.conopli.repository.MusicRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MusicRepositoryImpl implements MusicRepository {
    private final MusicJpaRepository jpaRepository;

    @Override
    public Music saveMusic(Music music) {
        try {
            return jpaRepository.save(music);
        } catch (Exception e) {
            throw new ServiceLogicException(ErrorCode.DATA_ACCESS_ERROR);
        }

    }
}
