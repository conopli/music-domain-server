package com.sideproject.conopli.constant;

import com.sideproject.conopli.exception.ServiceLogicException;
import lombok.Getter;

import java.util.Arrays;

public enum SearchType {
    // 1  제목, 2 가수 , 4 작사가 , 8 작곡가, 16 곡번호
    TITLE(1,"제목"),
    SINGER(2,"가수"),
    LYRICIST(4,"작사가"),
    COMPOSER(8,"작곡가"),
    NUM(16,"곡번호");

    @Getter
    final int parameter;

    @Getter
    final String name;

    SearchType(int parameter, String name) {
        this.parameter = parameter;
        this.name = name;
    }

    public static SearchType getSearchTypeName(int searchTypeInt) {
        return Arrays.stream(SearchType.values())
                .filter(type -> searchTypeInt == type.getParameter())
                .findFirst().orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND_SEARCH_TYPE));
    }
}
