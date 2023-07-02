package com.sideproject.conopli.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.sideproject.conopli.constant.ErrorCode;
import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.exception.ServiceLogicException;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.entity.QTjMusic;
import com.sideproject.conopli.music.entity.TjMusic;
import com.sideproject.conopli.repository.TjMusicJpaRepository;
import com.sideproject.conopli.repository.TjMusicRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TjMusicRepositoryImpl extends QuerydslRepositorySupport implements TjMusicRepository {
    public TjMusicRepositoryImpl(TjMusicJpaRepository jpaRepository) {
        super(TjMusic.class);
        this.jpaRepository = jpaRepository;
    }
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

    @Override
    public TjMusic findTjMusicById(Long musicId) {
        return verifiedMusicById(musicId);
    }

    @Override
    public TjMusic findTjMusicByNum(String num) {
        return verifiedMusic(num);
    }

    @Override
    public Page<MusicQueryDto> findQueryMusic(
            MusicNation nation,
            SearchType searchType,
            String keyWord,
            Pageable pageable
    ) {
        QTjMusic tjMusic = QTjMusic.tjMusic;
        String sortProperties = "";
        JPQLQuery<MusicQueryDto> query = from(tjMusic)
                .select(
                        Projections.constructor(
                                MusicQueryDto.class,
                                tjMusic.musicId,
                                tjMusic.num,
                                tjMusic.title,
                                tjMusic.singer,
                                tjMusic.lyricist,
                                tjMusic.composer,
                                tjMusic.youtubeUrl,
                                tjMusic.nation
                        ));
        if (nation != null) {
            query.where(
                    tjMusic.nation.eq(nation)
            );
        }
        if (searchType != null) {
            if (searchType.equals(SearchType.NUM)) {
                query.where(
                        tjMusic.num.containsIgnoreCase(keyWord)
                );
                sortProperties = "num";
            } else if (searchType.equals(SearchType.SINGER)) {
                query.where(
                        tjMusic.singer.containsIgnoreCase(keyWord)
                );
                sortProperties = "singer";
            } else if (searchType.equals(SearchType.LYRICIST)) {
                query.where(
                        tjMusic.lyricist.containsIgnoreCase(keyWord)
                );
                sortProperties = "lyricist";
            } else if (searchType.equals(SearchType.COMPOSER)) {
                query.where(
                        tjMusic.composer.containsIgnoreCase(keyWord)
                );
                sortProperties = "composer";
            } else if (searchType.equals(SearchType.TITLE)) {
                query.where(
                        tjMusic.title.containsIgnoreCase(keyWord)
                );
                sortProperties = "title";
            }
        }
        Pageable customPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(sortProperties).ascending()
        );
        List<MusicQueryDto> musicList = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.DATA_ACCESS_ERROR))
                .applyPagination(customPageable, query)
                .fetch();
        return new PageImpl<>(musicList, customPageable, query.fetchCount());
    }

    private void verifyMusic(String num) {
        if (jpaRepository.findByNum(num).isPresent()) {
            throw new ServiceLogicException(ErrorCode.EXISTS_MUSIC_NUM);
        }
    }
    private TjMusic verifiedMusic(String num) {
        return jpaRepository.findByNum(num)
                .orElseThrow(
                        () -> new ServiceLogicException(ErrorCode.NOT_FOUND_MUSIC)
                );
    }
    private TjMusic verifiedMusicById(Long musicId) {
        return jpaRepository.findById(musicId)
                .orElseThrow(
                        () -> new ServiceLogicException(ErrorCode.NOT_FOUND_MUSIC)
                );
    }
}
