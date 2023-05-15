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
@RequestMapping("/atcha/reviews")
@Tag(name = "ReviewController", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Movie 리뷰 작성 메서드", description = "Movie 리뷰 작성하는 메서드입니다.")
    @PostMapping("/movies/{movieId}")
    public ResponseEntity<Message> addReviewMovie(@PathVariable Long movieId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.addReviewMovie(movieId, requestDto, userDetails.getMember());
    }
    @Operation(summary = "TV 리뷰 작성 메서드", description = "TV 리뷰 작성하는 메서드입니다.")
    @PostMapping("/tvs/{tvId}")
    public ResponseEntity<Message> addReviewTv(@PathVariable Long tvId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.addReviewTv(tvId, requestDto, userDetails.getMember());
    }

    @Operation(summary = "Movie 리뷰 수정 메서드", description = "Movie 리뷰 수정하는 메서드입니다.")
    @PutMapping("/movies/{movieReviewId}")
    public ResponseEntity<Message> updateReviewMovie(@PathVariable Long movieReviewId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.updateReviewMovie(movieReviewId, requestDto, userDetails.getMember());
    }

    @Operation(summary = "TV 리뷰 수정 메서드", description = "TV 리뷰 수정하는 메서드입니다.")
    @PutMapping("/tvs/{tvReviewId}")
    public ResponseEntity<Message> updateReviewTv(@PathVariable Long tvReviewId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.updateReviewTv(tvReviewId, requestDto, userDetails.getMember());
    }

}
