package com.sideproject.conopli.music.dto;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.music.entity.TjMusic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicQueryDto {

    private Long musicId;

    private String num;

    private String title;

    private String singer;

    private String lyricist;

    private String composer;

    private String youtubeUrl;

    private MusicNation nation;

    private String kyNum;

    private boolean mrSound;

    MusicQueryDto(TjMusic music) {
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
    }

    public static MusicQueryDto of(TjMusic music) {
        return new MusicQueryDto(music);
    }

}
