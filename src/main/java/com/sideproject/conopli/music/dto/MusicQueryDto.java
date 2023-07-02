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

    public Long musicId;

    public String num;

    public String title;

    public String singer;

    public String lyricist;

    public String composer;

    public String youtubeUrl;

    public MusicNation nation;

    MusicQueryDto(TjMusic music) {
        this.musicId = music.getMusicId();
        this.num = music.getNum();
        this.title = music.getTitle();
        this.singer = music.getSinger();
        this.lyricist = music.getLyricist();
        this.composer = music.getComposer();
        this.youtubeUrl = music.getYoutubeUrl();
        this.nation = music.getNation();
    }

    public static MusicQueryDto of(TjMusic music) {
        return new MusicQueryDto(music);
    }

}
