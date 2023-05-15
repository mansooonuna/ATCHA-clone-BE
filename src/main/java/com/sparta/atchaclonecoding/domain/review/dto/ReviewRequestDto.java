package com.sparta.atchaclonecoding.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewRequestDto {
    private double star;
    private String content;
}
