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
public class NewMusic extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long newMusicId;

    @Column(nullable = false)
    int yy;

    @Column(nullable = false)
    int mm;


    @ToString.Exclude
    @OneToMany(mappedBy = "newMusic", cascade = CascadeType.DETACH)
    @OrderBy("musicId")
    private Set<TjMusic> musics = new LinkedHashSet<>();

    public void addMusic(TjMusic music) {
        this.musics.add(music);
    }
}
