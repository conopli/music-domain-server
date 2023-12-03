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
import org.springframework.restdocs.request.RequestDocumentation;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
        //When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/api/music/search?searchType=1&searchKeyWord=아리랑&page=0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        //Then
        mvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        document("SearchMusicGetAPI",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                queryParameters(
                                        parameterWithName("page").description("요청 페이지 정보"),
                                        parameterWithName("searchType").description("검색 타입 (1 = 제목, 2 = 가수 , 4 = 작사가 , 8 = 작곡가, 16 = 곡번호)"),
                                        parameterWithName("searchKeyWord").description("검색 키워드")
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
                                                fieldWithPath("data[].kyNum").type(JsonFieldType.STRING).description("KY 곡번호").optional(),
                                                fieldWithPath("data[].mrSound").type(JsonFieldType.BOOLEAN).description("MR 음원 여부"),
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
                .get("/api/music/new-music?yy=2023&mm=09&page=0")
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
                                queryParameters(
                                        parameterWithName("yy").description("검색 년도"),
                                        parameterWithName("mm").description("검색 월" ),
                                        parameterWithName("page").description("요청 페이지 정보")
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
                                                fieldWithPath("data[].kyNum").type(JsonFieldType.STRING).description("KY 곡번호").optional(),
                                                fieldWithPath("data[].mrSound").type(JsonFieldType.BOOLEAN).description("MR 음원 여부"),
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
    @DisplayName("[API][GET] Popular Music")
    void givenNoneWhenResultThenPopularMusic() throws Exception {
        //Given
        //When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/api/music/popular?searchType=1&yy=2023&mm=04&page=1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        //Then
        mvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        document("PopularMusicGetAPI",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                queryParameters(
                                        parameterWithName("searchType").description("검색 타입 (1 = 가요 2 = POP 3 = J-POP)"),
                                        parameterWithName("yy").description("연도"),
                                        parameterWithName("mm").description("월"),
                                        parameterWithName("page").description("요청 페이지 정보")
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
                                                fieldWithPath("data[].kyNum").type(JsonFieldType.STRING).description("KY 곡번호").optional(),
                                                fieldWithPath("data[].mrSound").type(JsonFieldType.BOOLEAN).description("MR 음원 여부"),
                                                fieldWithPath("data[].ranking").type(JsonFieldType.NUMBER).description("순위"),
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
    @DisplayName("[API][GET] Search Music By MusicNum")
    void givenMusicNumWhenGetSearchMusicThenReturnSearchMusic() throws Exception {
        //Given
        String musicNum = "83138";
        //When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/api/music/search/{musicNum}", musicNum)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        //Then
        mvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        document("SearchMusicByNumGetAPI",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("musicNum").description("곡 번호")
                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.musicId").type(JsonFieldType.NUMBER).description("음악 식별자"),
                                                fieldWithPath("data.num").type(JsonFieldType.STRING).description("곡번호"),
                                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("곡 제목"),
                                                fieldWithPath("data.singer").type(JsonFieldType.STRING).description("가수"),
                                                fieldWithPath("data.lyricist").type(JsonFieldType.STRING).description("작사"),
                                                fieldWithPath("data.composer").type(JsonFieldType.STRING).description("작곡"),
                                                fieldWithPath("data.youtubeUrl").type(JsonFieldType.STRING).description("YouTube URL"),
                                                fieldWithPath("data.nation").type(JsonFieldType.STRING).description("국가"),
                                                fieldWithPath("data.kyNum").type(JsonFieldType.STRING).description("KY 곡번호").optional(),
                                                fieldWithPath("data.mrSound").type(JsonFieldType.BOOLEAN).description("MR 음원 여부")
                                        )
                                )

                        ));
    }
}