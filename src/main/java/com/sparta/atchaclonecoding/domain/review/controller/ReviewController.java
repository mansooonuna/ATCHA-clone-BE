package com.sparta.atchaclonecoding.domain.review.controller;

import com.sparta.atchaclonecoding.domain.review.dto.ReviewRequestDto;
import com.sparta.atchaclonecoding.domain.review.service.ReviewService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/atcha/media")
@Tag(name = "ReviewController", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성 메서드", description = "리뷰 작성하는 메서드입니다.")
    @PostMapping("/{mediaId}")
    public ResponseEntity<Message> addReviewMovie(@PathVariable Long mediaId,
                                                  @RequestBody ReviewRequestDto requestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.addReviewMovie(mediaId, requestDto, userDetails.getMember());
    }


    @Operation(summary = "리뷰 수정 메서드", description = "리뷰 수정하는 메서드입니다.")
    @PutMapping("/{mediaId}/reviews/{mediaReviewId}")
    public ResponseEntity<Message> updateReviewMovie(@PathVariable Long mediaId, @PathVariable Long mediaReviewId,
                                                     @RequestBody ReviewRequestDto requestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.updateReviewMovie(mediaId, mediaReviewId, requestDto, userDetails.getMember());
    }

    @Operation(summary = "리뷰 삭제 메서드", description = "리뷰 삭제하는 메서드입니다.")
    @DeleteMapping("/{mediaId}/reviews/{mediaReviewId}")
    public ResponseEntity<Message> deleteReviewMovie(@PathVariable Long mediaId, @PathVariable Long mediaReviewId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.deleteReviewMovie(mediaId, mediaReviewId, userDetails.getMember());
    }

}
