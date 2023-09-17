package com.sideproject.conopli.music.service;

import com.sideproject.conopli.dto.ResponseDto;
import com.sideproject.conopli.music.dto.MusicDto;
import com.sideproject.conopli.music.entity.KyMusic;
import com.sideproject.conopli.repository.KyMusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sideproject.conopli.utils.CrawlingUrlUtil.createKySearchUrl;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KyMusicCrawlingService {

    private final KyMusicRepository kyMusicRepository;

    public ResponseDto KyMusicCrawlingByNum(String searchKeyWord) {
        try {
            String autoSearchUrl = createKySearchUrl(searchKeyWord);
            Document doc = Jsoup.connect(autoSearchUrl).get();
            // #search_chart_frm_1 > div > ul:nth-child(2)
            Elements list = doc.select("#search_chart_frm_1>div>ul:nth-child(2)");
            MusicDto response = null;
            for (Element element : list) {
                Elements select = element.select("ul>li");
                if (!select.isEmpty()) {
                    List<Element> elementList = select.stream().toList();
                    String title = elementList.get(2).select("li>span:nth-child(1)").text();
                    List<String> bodyList = elementList.stream().map(Element::text).toList();
                    response = MusicDto.of(title, bodyList);
                    KyMusic music = KyMusic.of(response);
                    KyMusic save = kyMusicRepository.saveMusic(music);
                    if (save == null) {
                        response = null;
                    }
                }
            }
            return ResponseDto.of(response);
        } catch (Exception e) {
            return ResponseDto.of(e);
        }
    }
}
