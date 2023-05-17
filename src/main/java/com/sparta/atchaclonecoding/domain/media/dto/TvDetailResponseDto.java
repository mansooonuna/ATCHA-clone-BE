package com.sparta.atchaclonecoding.domain.media.dto;

import com.sparta.atchaclonecoding.domain.casting.dto.CastingResponseDto;
import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.entity.MediaType;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TvDetailResponseDto {
    private Long tvId;
    private MediaType category;
    private String title;
    private double star;
    private String genre;
    private String age;
    private String image;
    private String information;
    private List<CastingResponseDto> castingList;
    private List<ReviewResponseDto> reviewList;


    public TvDetailResponseDto(Media media, List<ReviewResponseDto> reviewList, List<CastingResponseDto> castingList) {
        this.tvId = media.getId();
        this.category = media.getCategory();
        this.title = media.getTitle();
        this.image = media.getImage();
        this.genre = media.getGenre();
        this.star = media.getStar();
        this.age = media.getAge();
        this.information = media.getInformation();
        this.castingList = castingList;
        this.reviewList = reviewList;
    }
}


