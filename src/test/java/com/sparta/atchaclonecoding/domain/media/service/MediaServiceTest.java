package com.sparta.atchaclonecoding.domain.media.service;


import com.sparta.atchaclonecoding.domain.casting.repository.CastingRepository;
import com.sparta.atchaclonecoding.domain.media.dto.MediaRecommendResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.MediaResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.MovieDetailResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.TvDetailResponseDto;
import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.entity.MediaType;
import com.sparta.atchaclonecoding.domain.media.repository.MediaRepository;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.review.repository.ReviewRepository;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.util.Message;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.atchaclonecoding.exception.ErrorCode.MOVIE_NOT_FOUND;
import static com.sparta.atchaclonecoding.exception.ErrorCode.TV_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@DisplayName("Media Service 테스트")
@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private CastingRepository castingRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private MediaService mediaService;


    @DisplayName("전체 조회 테스트")
    @Test
    void getMediaList() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        when(mediaRepository.findAll(pageable)).thenReturn(Page.empty());
        // When
        ResponseEntity<Page<Media>> response = mediaService.getMediaList(new Member(), pageable);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("영화 전체 조회 테스트")
    @Test
    void getMovies() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        when(mediaRepository.findAllByCategory(MediaType.MOVIE, pageable)).thenReturn(Page.empty());
        // When
        ResponseEntity<Page<Media>> response = mediaService.getMovies(new Member(), pageable);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("TV 전체 조회 테스트")
    @Test
    void getTvs() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        when(mediaRepository.findAllByCategory(MediaType.TV, pageable)).thenReturn(Page.empty());
        // When
        ResponseEntity<Page<Media>> response = mediaService.getTvs(new Member(), pageable);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("영화 상세 조회 성공 테스트")
    @Test
    void getMovie() {
        // Given
        when(mediaRepository.findByIdAndCategory(anyLong(), eq(MediaType.MOVIE))).thenReturn(Optional.of(new Media()));
        when(castingRepository.findAllByMediaId(anyLong())).thenReturn(List.of());
        when(reviewRepository.findAllByMediaId(anyLong())).thenReturn(List.of());
        // When
        ResponseEntity<MovieDetailResponseDto> response = mediaService.getMovie(1L, new Member());
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("영화 상세 조회 실패 테스트 - 영화 없음")
    @Test
    void getMovie_movieNotFound() {
        // Given
        when(mediaRepository.findByIdAndCategory(anyLong(), eq(MediaType.MOVIE))).thenReturn(Optional.empty());
        // When
        CustomException exception = assertThrows(CustomException.class, () -> mediaService.getMovie(1L, new Member()));
        // Then
        assertEquals(MOVIE_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("TV 상세 조회 성공 테스트")
    @Test
    void getTv() {
        // Given
        when(mediaRepository.findByIdAndCategory(anyLong(), eq(MediaType.TV))).thenReturn(Optional.of(new Media()));
        when(castingRepository.findAllByMediaId(anyLong())).thenReturn(List.of());
        when(reviewRepository.findAllByMediaId(anyLong())).thenReturn(List.of());
        // When
        ResponseEntity<TvDetailResponseDto> response = mediaService.getTv(1L, new Member());
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("TV 상세 조회 실패 테스트 - TV 없음")
    @Test
    void getTv_tvNotFound() {
        // Given
        when(mediaRepository.findByIdAndCategory(anyLong(), eq(MediaType.TV))).thenReturn(Optional.empty());
        // When
        CustomException exception = assertThrows(CustomException.class, () -> mediaService.getTv(1L, new Member()));
        // Then
        assertEquals(TV_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("전체 검색 성공 테스트")
    @Test
    void searchTv() {
        // Given
        when(mediaRepository.findAllBySearchKeyword(anyString())).thenReturn(List.of());
        // When
        ResponseEntity<Message> response = mediaService.searchTv("keyword", new Member());
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @DisplayName("전체 검색 성공 테스트 - 검색 결과 없음")
    @Test
    void searchTv_notFoundResult() {
//        // Given
//        List<Media> value = new ArrayList<>();
//        when(mediaRepository.findAllByOrderByStarDesc()).thenReturn(value);
//        // When
//        ResponseEntity<Message> response = mediaService.searchTv("str", new Member());
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("검색 결과 없음", response.getBody().getMessage());
//        assertNotNull(response.getBody().getData());
    }



    @DisplayName("영화/TV 추천 성공 테스트")
    @Test
    void recommendAll() {
        // Given
        when(mediaRepository.findAllByOrderByStarDesc()).thenReturn(List.of());
        // When
        ResponseEntity<MediaRecommendResponseDto> response = mediaService.recommendAll(new Member());
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @DisplayName("영화/TV 추천 성공 테스트 - media가 8개 이상")
    @Test
    void recommendAll_8() {
        // Given
        List<Media> value = new ArrayList<>();
        for (int i = 0; i < 10; i++) value.add(new Media());

        when(mediaRepository.findAllByOrderByStarDesc()).thenReturn(value);
        // When
        ResponseEntity<MediaRecommendResponseDto> response = mediaService.recommendAll(new Member());
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}