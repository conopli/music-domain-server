package com.sideproject.conopli.music.entity;


import com.sideproject.conopli.audit.Auditable;
import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.music.dto.MusicDto;
import com.sideproject.conopli.music.dto.MusicQueryDto;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class PopularMusicEntity extends Auditable {
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

    @Column(nullable = false)
    @Setter
    String ranking;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private PopularMusic popularMusic;

    public void addPopularMusic(PopularMusic popularMusic) {
        this.popularMusic = popularMusic;
        popularMusic.addMusic(this);
    }

    PopularMusicEntity(TjMusic music, String ranking) {
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
        this.ranking = ranking;
    }

    public static PopularMusicEntity of(TjMusic music, String ranking) {
        return new PopularMusicEntity(music, ranking);
    }

}
