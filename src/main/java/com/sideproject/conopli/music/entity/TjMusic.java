package com.sideproject.conopli.music.entity;


import com.sideproject.conopli.audit.Auditable;
import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.music.dto.MusicDto;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class TjMusic extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long musicId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    MusicNation nation;

    @Column(nullable = false)
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
    String youtubeUrl;

    @Column(nullable = true)
    @Setter
    String kyNum;

    @Column(nullable = false)
    @Setter
    boolean mrSound;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private NewMusic newMusic;

    public void addNewMusic(NewMusic newMusic) {
        this.newMusic = newMusic;
        newMusic.addMusic(this);
    }

    public static TjMusic of(MusicDto dto) {
        return new TjMusic(dto);
    }

    TjMusic(MusicDto dto) {
        this.num = dto.getNum();
        this.title = dto.getTitle();
        this.singer = dto.getSinger();
        this.lyricist = dto.getLyricist();
        this.composer = dto.getComposer();
        this.youtubeUrl = dto.getYoutubeUrl();
        this.nation = MusicNation.valueOf(dto.getNation());
    }
}
