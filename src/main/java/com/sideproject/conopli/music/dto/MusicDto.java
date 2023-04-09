package com.sideproject.conopli.music.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicDto {

    public String num;
    public String title;

    public String singer;

    public String lyricist;

    public String composer;

    public String youtubeUrl;

    public String nation;

    public static MusicDto of(List<String> bodyList, String nation) {
        return new MusicDto(bodyList, nation);
    }
    MusicDto(List<String> bodyList, String nation) {
        this.num = bodyList.get(0);
        this.title = bodyList.get(1);
        this.singer = bodyList.get(2);
        this.lyricist = bodyList.get(3);
        this.composer = bodyList.get(4);
        this.youtubeUrl = "https://www.youtube.com/user/ziller/search?query=" + bodyList.get(0);
        this.nation = nation;

    }
}
