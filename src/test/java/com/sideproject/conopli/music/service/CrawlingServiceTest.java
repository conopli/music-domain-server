package com.sideproject.conopli.music.service;

import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class CrawlingServiceTest {

    @Autowired
    private CrawlingService service;


    @Test
    void test() {
        // 35000 까지 완료
//        for (int i = 35001; i <= 100000; i++) {
//            String num = String.valueOf(i);
//            ResponseDto autoSearch = service.createMusicCrawling(num, MusicNation.KOR);
//            System.out.println(MusicNation.KOR.getNation()+num +" = "+autoSearch);
//        }
        for (int i = 1; i <= 100000; i++) {
            String num = String.valueOf(i);
            ResponseDto autoSearch = service.createMusicCrawling(num, MusicNation.ENG);
            System.out.println(MusicNation.ENG.getNation()+num + " = "+autoSearch);
        }
        for (int i = 1; i <= 100000; i++) {
            String num = String.valueOf(i);
            ResponseDto autoSearch = service.createMusicCrawling(num, MusicNation.JPN);
            System.out.println(MusicNation.JPN.getNation()+num +" = "+autoSearch);
        }
    }
}