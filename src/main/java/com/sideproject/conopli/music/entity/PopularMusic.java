package com.sideproject.conopli.music.entity;


import com.sideproject.conopli.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class PopularMusic extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long popularMusicId;

    @Column(nullable = false)
    int yy;

    @Column(nullable = false)
    int mm;

    @Column(nullable = false)
    int searchType;

    @ToString.Exclude
    @OneToMany(mappedBy = "popularMusic", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("musicId")
    private Set<PopularMusicEntity> musics = new LinkedHashSet<>();

    public void addMusic(PopularMusicEntity music) {
        this.musics.add(music);
    }

}
