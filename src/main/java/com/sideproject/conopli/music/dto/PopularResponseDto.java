package com.sideproject.conopli.music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularResponseDto {
    public String ranking;

    public String num;

    public String title;

    public String singer;
    public static PopularResponseDto of(List<String> bodyList) {
        return new PopularResponseDto(bodyList);
    }

    PopularResponseDto(List<String> bodyList) {
        this.ranking = bodyList.get(0);
        this.num = bodyList.get(1);
        this.title = bodyList.get(2);
        this.singer = bodyList.get(3);
    }
}
