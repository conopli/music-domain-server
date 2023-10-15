package com.sideproject.conopli.music.service;


import com.sideproject.conopli.constant.MusicNation;
import com.sideproject.conopli.dto.ResponseDto;
import com.sideproject.conopli.music.dto.MusicDto;
import com.sideproject.conopli.music.dto.PopularRequestDto;
import com.sideproject.conopli.music.dto.PopularResponseDto;
import com.sideproject.conopli.music.dto.SearchRequestDto;
import com.sideproject.conopli.music.entity.TjMusic;
import com.sideproject.conopli.repository.TjMusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sideproject.conopli.utils.CrawlingUrlUtil.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TjMusicCrawlingService {

    private final TjMusicRepository tjMusicRepository;

    public ResponseDto getSearchCrawling(SearchRequestDto dto) {
        try {
            String url = createTjSearchUrl(dto);
            log.info("Request Url = {}", url);
            Document doc = Jsoup.connect(url).get();
            Elements list =doc.select(".board_type1 tbody>tr");
            List<MusicDto> response = new ArrayList<>();
            for (Element element : list) {
                Elements select = element.select("tr>td");
                if (!select.isEmpty()) {
                    List<String> bodyList = select.stream().map(Element::text).toList();
                    MusicDto of = MusicDto.of(bodyList,dto.getSearchNation());
                    response.add(of);
                }
            }
            return ResponseDto.of(response);
        } catch (Exception e) {
            return ResponseDto.of(e.getMessage());
        }
    }

    public ResponseDto getPopularCrawling(PopularRequestDto dto) {
        try {
            String popularUrl = createTjPopularUrl(dto);
            log.info("Request Url = {}", popularUrl);
            Document doc = Jsoup.connect(popularUrl).get();
            Elements list =doc.select(".board_type1 tbody>tr");
            List<PopularResponseDto> response = new ArrayList<>();
            for (Element element : list) {
                Elements select = element.select("tr>td");
                if (!select.isEmpty()) {
                    List<String> bodyList = select.stream().map(Element::text).toList();
                    PopularResponseDto of = PopularResponseDto.of(bodyList);
                    response.add(of);
                }
            }
            return ResponseDto.of(response);
        } catch (Exception e) {
            return ResponseDto.of(e.getMessage());
        }
    }

    public ResponseDto getNewMusicCrawling() {
        try {
//Todo 달 변경으로 인해 정상 파싱 되지 않음 임시 조치
            String newSongUrl = createTjNewSongUrl(
                    String.valueOf(LocalDateTime.now().getYear()),
                    String.format("%02d", LocalDateTime.now().getMonthValue()-1)
            );
            log.info("Request Url = {}", newSongUrl);
            Document doc = Jsoup.connect(newSongUrl).get();
            Elements list =doc.select(".board_type1 tbody>tr");
            List<MusicDto> response = new ArrayList<>();
            for (Element element : list) {
                Elements select = element.select("tr>td");
                if (!select.isEmpty()) {
                    List<String> bodyList = select.stream().map(Element::text).toList();
                    MusicDto of = MusicDto.of(bodyList,"NEW");
                    response.add(of);
                }
            }
            return ResponseDto.of(response);
        } catch (Exception e) {
            return ResponseDto.of(e.getMessage());
        }
    }

    public ResponseDto createMusicCrawling(String searchKeyWord, MusicNation nation) {
        try {
            String searchNation = nation.getNation();
            String autoSearchUrl = createTjAutoSearchUrl(searchKeyWord, searchNation);
            Document doc = Jsoup.connect(autoSearchUrl).get();
            Elements list = doc.select(".board_type1 tbody>tr");
            List<MusicDto> response = new ArrayList<>();
            for (Element element : list) {
                Elements select = element.select("tr>td");
                if (!select.isEmpty()) {
                    List<String> bodyList = select.stream().map(Element::text).toList();
                    MusicDto of = MusicDto.of(bodyList, searchNation);
                    response.add(of);
                    TjMusic entity = TjMusic.of(of);
                    tjMusicRepository.saveMusic(entity);
                }
            }
            return ResponseDto.of(response);
        } catch (Exception e) {
            return ResponseDto.of(e.getMessage());
        }
    }

    private String filteringSingerChangeMatchingForKyMusic(String singer) {
        if (singer.contains("트와이스")) {
            return singer.replaceAll("트와이스", "트와이스(TWICE)");
        } else if (singer.contains("IU")) {
            return singer.replaceAll("IU", "아이유(IU)");
        } else if (singer.contains("K.Will")) {
            return singer.replaceAll("K.Will", "케이윌(K.Will)");
        } else if (singer.contains("BTOB")) {
            return singer.replaceAll("BTOB", "비투비(BTOB)");
        } else if (singer.contains("워너원")) {
            return singer.replaceAll("워너원", "워너원(Wanna One)");
        } else if (singer.contains("tei")) {
            return singer.replaceAll("tei", "테이(TEI)");
        } else {
            return singer;
        }
    }

}

