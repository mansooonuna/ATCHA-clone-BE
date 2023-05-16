package com.sparta.atchaclonecoding.domain.review.dto;

import com.sparta.atchaclonecoding.domain.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReviewResponseDto {
    private Long reviewId;
    private String nickname;
    private double star;
    private String content;
    private LocalDateTime createdAt;

    public ReviewResponseDto(Review review){
        this.reviewId = review.getId();
        this.nickname = review.getMember().getNickname();
        this.star = review.getStar();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }

}
