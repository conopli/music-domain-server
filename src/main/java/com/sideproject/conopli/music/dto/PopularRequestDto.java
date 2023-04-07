package com.sideproject.conopli.music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularRequestDto {
    // 1 = 가요 2 = POP 3 = J-POP
    String searchType;

    String syy;
    String smm;

    String eyy;
    String emm;



}
