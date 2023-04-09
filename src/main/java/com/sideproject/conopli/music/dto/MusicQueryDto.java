package com.sideproject.conopli.music.dto;

import com.sideproject.conopli.constant.MusicNation;
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

}
