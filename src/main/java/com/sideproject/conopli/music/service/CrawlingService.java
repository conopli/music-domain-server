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
public class CrawlingService {

    private final TjMusicRepository tjMusicRepository;

    public ResponseDto getSearchCrawling(SearchRequestDto dto) {
        try {
            String url = createSearchUrl(dto);
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
            String popularUrl = createPopularUrl(dto);
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

            String newSongUrl = createNewSongUrl(
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
            String autoSearchUrl = createAutoSearchUrl(searchKeyWord, searchNation);
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

}
