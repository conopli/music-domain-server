package com.sideproject.conopli.music.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.conopli.music.dto.PopularRequestDto;
import com.sideproject.conopli.music.dto.SearchRequestDto;
import com.sideproject.conopli.music.service.MusicService;
import com.sideproject.conopli.utils.ApiDocumentUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("local")
class MusicControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MusicService musicService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @DisplayName("[API][GET] Music Search")
    void givenSearchRequestWhenResultThenRequestKeyWord() throws Exception {
        //Given
        SearchRequestDto searchRequestDto = new SearchRequestDto(
                1, 0,"아리랑","KOR"
        );
        String content = mapper.writeValueAsString(searchRequestDto);
        //When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/api/music/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        //Then
        mvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        document("SearchMusicGetAPI",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                requestFields(
                                        List.of(
                                                fieldWithPath("searchType").type(JsonFieldType.NUMBER).description("검색 타입"),
                                                fieldWithPath("searchPage").type(JsonFieldType.NUMBER).description("검색 페이지"),
                                                fieldWithPath("searchKeyWord").type(JsonFieldType.STRING).description("검색 키워드"),
                                                fieldWithPath("searchNation").type(JsonFieldType.STRING).description("검색 국가")
                                        )

                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data[].musicId").type(JsonFieldType.NUMBER).description("모델 식별자"),
                                                fieldWithPath("data[].num").type(JsonFieldType.STRING).description("곡번호"),
                                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("곡 제목"),
                                                fieldWithPath("data[].singer").type(JsonFieldType.STRING).description("가수"),
                                                fieldWithPath("data[].lyricist").type(JsonFieldType.STRING).description("작사"),
                                                fieldWithPath("data[].composer").type(JsonFieldType.STRING).description("작곡"),
                                                fieldWithPath("data[].youtubeUrl").type(JsonFieldType.STRING).description("YouTube URL"),
                                                fieldWithPath("data[].nation").type(JsonFieldType.STRING).description("국가"),
                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 요소 갯수"),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 요소"),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지")


                                                )
                                )

                        ));
    }

    @Test
    @DisplayName("[API][GET] New Music")
    void givenNoneWhenResultThenNewMusic() throws Exception {
        //Given
        //When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/api/music/new-music")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        //Then
        mvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        document("NewMusicGetAPI",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                responseFields(
                                        List.of(
                                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data[].num").type(JsonFieldType.STRING).description("곡번호"),
                                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("곡 제목"),
                                                fieldWithPath("data[].singer").type(JsonFieldType.STRING).description("가수"),
                                                fieldWithPath("data[].lyricist").type(JsonFieldType.STRING).description("작사"),
                                                fieldWithPath("data[].composer").type(JsonFieldType.STRING).description("작곡"),
                                                fieldWithPath("data[].youtubeUrl").type(JsonFieldType.STRING).description("YouTube URL"),
                                                fieldWithPath("data[].nation").type(JsonFieldType.STRING).description("국가")
                                        )
                                )

                        ));
    }

    @Test
    @DisplayName("[API][GET] Popular Music")
    void givenNoneWhenResultThenPopularMusic() throws Exception {
        //Given
        PopularRequestDto popularRequestDto = new PopularRequestDto(
                "1","2023","04","2023","04"
        );
        String content = mapper.writeValueAsString(popularRequestDto);
        //When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/api/music/popular")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        //Then
        mvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        document("PopularMusicGetAPI",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                requestFields(
                                        List.of(
                                                fieldWithPath("searchType").type(JsonFieldType.STRING).description("검색 타입"),
                                                fieldWithPath("syy").type(JsonFieldType.STRING).description("시작 연도"),
                                                fieldWithPath("smm").type(JsonFieldType.STRING).description("시작 월"),
                                                fieldWithPath("eyy").type(JsonFieldType.STRING).description("종료 연도"),
                                                fieldWithPath("emm").type(JsonFieldType.STRING).description("종료 월")
                                        )

                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data[].ranking").type(JsonFieldType.STRING).description("순위"),
                                                fieldWithPath("data[].num").type(JsonFieldType.STRING).description("곡 번호"),
                                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("곡 제목"),
                                                fieldWithPath("data[].singer").type(JsonFieldType.STRING).description("가수")
                                        )
                                )

                        ));
    }
}