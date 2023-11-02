package com.sideproject.conopli.music.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewMusicDto {

    Long newMusicId;

    int yy;

    int mm;

    List<MusicQueryDto> newMusic;
}
