package com.sideproject.conopli.repository.impl;

import com.querydsl.jpa.JPQLQuery;
import com.sideproject.conopli.constant.ErrorCode;
import com.sideproject.conopli.constant.SearchType;
import com.sideproject.conopli.exception.ServiceLogicException;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.entity.KyMusic;
import com.sideproject.conopli.music.entity.QKyMusic;
import com.sideproject.conopli.music.entity.TjMusic;
import com.sideproject.conopli.repository.KyMusicJpaRepository;
import com.sideproject.conopli.repository.KyMusicRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KyMusicRepositoryImpl extends QuerydslRepositorySupport implements KyMusicRepository {

    public KyMusicRepositoryImpl(KyMusicJpaRepository jpaRepository) {
        super(KyMusic.class);
        this.jpaRepository = jpaRepository;
    }

    private final KyMusicJpaRepository jpaRepository;


    @Override
    public KyMusic saveMusic(KyMusic music) {
        Optional<KyMusic> findMusic = jpaRepository.findFirstByNum(music.getNum());
        if (findMusic.isPresent()) {
            return null;
        } else {
            return jpaRepository.save(music);
        }
    }

    @Override
    public KyMusic findMusicById(Long kyMusicId) {
        return jpaRepository.findById(kyMusicId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND_MUSIC));
    }

    @Override
    public KyMusic findQueryMusic(List<String> title, List<String> signer) {
        QKyMusic kyMusic = QKyMusic.kyMusic;
        JPQLQuery<KyMusic> query = from(kyMusic).select(kyMusic);
        if (title != null) {
            for (String keyWord : title) {
                query.where(
                        kyMusic.title.eq(keyWord).or(kyMusic.title.containsIgnoreCase(keyWord))
                );
            }
        }
        if (signer != null) {
            for (String keyWord : signer) {
                query.where(
                        kyMusic.singer.eq(keyWord).or(kyMusic.singer.containsIgnoreCase(keyWord))
                );
            }
        }
        if (title == null && signer == null) {
            return null;
        } else {
            return query.fetchFirst();
        }

    }

    @Override
    public Page<KyMusic> findQueryMusic(SearchType searchType, List<String> keyWords, Pageable pageable) {
        QKyMusic kyMusic = QKyMusic.kyMusic;
        JPQLQuery<KyMusic> query = from(kyMusic).select(kyMusic);
        for (String keyWord : keyWords) {
            if (searchType != null) {
                if (searchType.equals(SearchType.NUM)) {
                    query.where(
                            kyMusic.num.containsIgnoreCase(keyWord)
                    );
                } else if (searchType.equals(SearchType.SINGER)) {
                    query.where(
                            kyMusic.singer.eq(keyWord).or(kyMusic.singer.containsIgnoreCase(keyWord))
                    );
                } else if (searchType.equals(SearchType.LYRICIST)) {
                    query.where(
                            kyMusic.lyricist.eq(keyWord).or(kyMusic.lyricist.containsIgnoreCase(keyWord))
                    );
                } else if (searchType.equals(SearchType.COMPOSER)) {
                    query.where(
                            kyMusic.composer.eq(keyWord).or(kyMusic.composer.containsIgnoreCase(keyWord))
                    );
                } else if (searchType.equals(SearchType.TITLE)) {
                    query.where(
                            kyMusic.title.eq(keyWord).or(kyMusic.title.containsIgnoreCase(keyWord))
                    );
                }
            }
        }
        List<KyMusic> musicList = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.DATA_ACCESS_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(musicList, pageable, query.fetchCount());
    }

}
