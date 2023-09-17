package com.sideproject.conopli.music.entity;


import com.sideproject.conopli.audit.Auditable;
import com.sideproject.conopli.music.dto.MusicDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class KyMusic extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long musicId;

    @Column(nullable = false,unique = true)
    String num;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String singer;

    @Column(nullable = false)
    String lyricist;

    @Column(nullable = false)
    String composer;

    @Column(nullable = false)
    boolean tjMappedCheck;


    public static KyMusic of(MusicDto dto) {
        return new KyMusic(dto);
    }

    KyMusic(MusicDto dto) {
        this.num = dto.getNum();
        this.title = dto.getTitle();
        this.singer = dto.getSinger();
        this.lyricist = dto.getLyricist();
        this.composer = dto.getComposer();
    }
}
