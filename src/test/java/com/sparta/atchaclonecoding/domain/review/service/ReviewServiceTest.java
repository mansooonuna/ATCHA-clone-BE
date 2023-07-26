package com.sparta.atchaclonecoding.domain.review.service;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.repository.MediaRepository;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewRequestDto;
import com.sparta.atchaclonecoding.domain.review.entity.Review;
import com.sparta.atchaclonecoding.domain.review.repository.ReviewRepository;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.util.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sparta.atchaclonecoding.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("Review Service 테스트")
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private MediaRepository mediaRepository;
    @InjectMocks
    private ReviewService reviewService;


    @DisplayName("리뷰 작성 성공 테스트")
    @Test
    void addReviewMedia() {
        // Given
        ReviewRequestDto requestDto = new ReviewRequestDto(4.5, "재미있어요!");
        when(mediaRepository.findById(anyLong())).thenReturn(Optional.of(new Media()));
        // When
        ResponseEntity<Message> response = reviewService.addReviewMedia(1L, requestDto, new Member());
        // Then
        assertEquals("리뷰 작성 성공", response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("리뷰 작성 실패 테스트 - 리뷰 내용 없음")
    @Test
    void addReviewMedia_noContent() {
        // Given
        ReviewRequestDto requestDto = new ReviewRequestDto(4.5, "");
        when(mediaRepository.findById(anyLong())).thenReturn(Optional.of(new Media()));
        // When
        CustomException exception = assertThrows(CustomException.class, () -> reviewService.addReviewMedia(1L, requestDto, new Member()));
        // Then
        assertEquals(NON_CONTENT, exception.getErrorCode());
    }

    @DisplayName("리뷰 작성 실패 테스트 - 리뷰할 media 없음")
    @Test
    void addReviewMedia_noMedia() {
        // Given
        ReviewRequestDto requestDto = new ReviewRequestDto(4.5, "");
        when(mediaRepository.findById(anyLong())).thenReturn(Optional.empty());
        // When
        CustomException exception = assertThrows(CustomException.class, () -> reviewService.addReviewMedia(1L, requestDto, new Member()));
        // Then
        assertEquals(MEDIA_NOT_FOUND, exception.getErrorCode());
    }

    @DisplayName("리뷰 수정 테스트")
    @Test
    void updateReviewMedia() {
        // Given
        ReviewRequestDto requestDto = new ReviewRequestDto(4.5, "재미있어요!");
        when(mediaRepository.findById(anyLong())).thenReturn(Optional.of(new Media()));
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(new Review()));
        // When
        ResponseEntity<Message> response = reviewService.updateReviewMedia(1L, 1L, requestDto, new Member());
        // Then
        assertEquals("리뷰 수정 성공", response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @DisplayName("리뷰 삭제 성공 테스트")
    @Test
    void deleteReviewMedia() {
        // Given
        when(mediaRepository.findById(anyLong())).thenReturn(Optional.of(new Media()));
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(new Review()));
        // When
        ResponseEntity<Message> response = reviewService.deleteReviewMedia(1L, 1L, new Member());
        // Then
        assertEquals("리뷰 삭제 성공", response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("리뷰 삭제 실패 테스트 - 리뷰 이미 삭제됨")
    @Test
    void deleteReviewMedia_alreadyDeleted() {
        // Given
        when(mediaRepository.findById(anyLong())).thenReturn(Optional.of(new Media()));
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());
        // When
        CustomException exception = assertThrows(CustomException.class, () -> reviewService.deleteReviewMedia(1L, 1L, new Member()));
        // Then
        assertEquals(REVIEW_NOT_FOUND, exception.getErrorCode());
    }
}