package com.sideproject.conopli.constant;

import lombok.Getter;

public enum PopularSearchType {
    // 1 = 가요 2 = POP 3 = J-POP
    KOR(1, "가요"),
    POP(2, "POP"),
    J_POP(3, "J-POP");

    @Getter
    final int parameter;

    @Getter
    final String name;


    PopularSearchType(int parameter, String name) {
        this.parameter = parameter;
        this.name = name;
    }
}
