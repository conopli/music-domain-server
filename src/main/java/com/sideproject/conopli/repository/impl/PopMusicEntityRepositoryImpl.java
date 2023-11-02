package com.sideproject.conopli.repository.impl;

import com.querydsl.jpa.JPQLQuery;
import com.sideproject.conopli.constant.ErrorCode;
import com.sideproject.conopli.exception.ServiceLogicException;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import com.sideproject.conopli.music.entity.PopularMusicEntity;
import com.sideproject.conopli.music.entity.QPopularMusicEntity;
import com.sideproject.conopli.repository.PopMusicEntityJpaRepository;
import com.sideproject.conopli.repository.PopMusicEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PopMusicEntityRepositoryImpl extends QuerydslRepositorySupport implements PopMusicEntityRepository {

    public PopMusicEntityRepositoryImpl(PopMusicEntityJpaRepository jpaRepository) {
        super(PopularMusicEntity.class);
        this.jpaRepository = jpaRepository;
    }

    private final PopMusicEntityJpaRepository jpaRepository;

    @Override
    public Page<PopularMusicEntity> findPopularMusic(int yy, int mm, int searchType, Pageable pageable) {
        QPopularMusicEntity popularMusic = QPopularMusicEntity.popularMusicEntity;

        JPQLQuery<PopularMusicEntity> query = from(popularMusic)
                .select(popularMusic);
        query.where(
                popularMusic.popularMusic.yy.eq(yy).and(popularMusic.popularMusic.mm.eq(mm)).and(popularMusic.popularMusic.searchType.eq(searchType))
        );
        return getPageImpl(query, pageable);
    }

    @Override
    public PopularMusicEntity savePopularEntityMusic(PopularMusicEntity music) {
        return jpaRepository.save(music);
    }

    private Page<PopularMusicEntity> getPageImpl(JPQLQuery<PopularMusicEntity> query, Pageable pageable) {
        List<PopularMusicEntity> musicList = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.DATA_ACCESS_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(musicList, pageable, query.fetchCount());
    }
}
