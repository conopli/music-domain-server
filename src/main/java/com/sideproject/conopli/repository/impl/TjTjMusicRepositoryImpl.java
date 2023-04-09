package com.sideproject.conopli.repository.impl;

import com.sideproject.conopli.constant.ErrorCode;
import com.sideproject.conopli.exception.ServiceLogicException;
import com.sideproject.conopli.music.entity.TjMusic;
import com.sideproject.conopli.repository.TjMusicJpaRepository;
import com.sideproject.conopli.repository.TjMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TjTjMusicRepositoryImpl implements TjMusicRepository {
    private final TjMusicJpaRepository jpaRepository;

    @Override
    public TjMusic saveMusic(TjMusic music) {
        try {
            verifyMusic(music.getNum());
            return jpaRepository.save(music);
        } catch (ServiceLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceLogicException(ErrorCode.DATA_ACCESS_ERROR);
        }

    }

    private void verifyMusic(String num) {
        if (jpaRepository.findByNum(num).isPresent()) {
            throw new ServiceLogicException(ErrorCode.EXISTS_MUSIC_NUM);
        }
    }
}
