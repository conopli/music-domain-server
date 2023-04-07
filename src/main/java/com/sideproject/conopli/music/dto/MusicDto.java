package com.sideproject.conopli.music.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
