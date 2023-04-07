package com.sideproject.conopli.music.entity;


import com.sideproject.conopli.constant.MusicNation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Music {
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

    @CreatedDate
    LocalDateTime createAt;

    @LastModifiedDate
    LocalDateTime modifyAt;

}
