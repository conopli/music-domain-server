package com.sideproject.conopli.music.dto;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.music.entity.PopularMusicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopularMusicResponseDto {

    Long musicId;

    MusicNation nation;

    String num;

    String title;

    String singer;

    String lyricist;

    String composer;

    String youtubeUrl;

    String kyNum;

    boolean mrSound;

    Long ranking;

    PopularMusicResponseDto(PopularMusicEntity music) {
        this.musicId = music.getMusicId();
        this.num = music.getNum();
        this.title = music.getTitle();
        this.singer = music.getSinger();
        this.lyricist = music.getLyricist();
        this.composer = music.getComposer();
        this.youtubeUrl = music.getYoutubeUrl();
        this.nation = music.getNation();
        this.kyNum = music.getKyNum();
        this.mrSound = music.isMrSound();
        this.ranking = music.getRanking();
    }

    public static PopularMusicResponseDto of(PopularMusicEntity music) {
        return new PopularMusicResponseDto(music);
    }

}
