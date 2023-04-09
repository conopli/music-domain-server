package com.sideproject.conopli.constant;

import lombok.Getter;

public enum MusicNation {
    // KOR, ENG, JPN
    KOR("KOR"),
    ENG("ENG"),
    JPN("JPN");

    @Getter
    final String nation;

    MusicNation(String nation) {
        this.nation = nation;
    }
}
