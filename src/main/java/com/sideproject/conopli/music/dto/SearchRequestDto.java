package com.sideproject.conopli.music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDto {
    // 1  제목, 2 가수 , 4 작사가 , 8 작곡가, 16 곡번호
    int searchType;
    int searchPage;
    // 검색어 없으면 에러
    String searchKeyWord;
    // KOR, ENG, JPN
    String searchNation;
}
