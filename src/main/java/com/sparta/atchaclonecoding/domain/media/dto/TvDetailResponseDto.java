package com.sparta.atchaclonecoding.domain.media.dto;

import com.sparta.atchaclonecoding.domain.casting.dto.CastingResponseDto;
import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TvDetailResponseDto {
    private Long tvId;
    private String title;
    private double star;
    private String genre;
    private String age;
    private String image;
    private String information;
    private List<CastingResponseDto> castingResponseDtos;
    private List<ReviewResponseDto> reviewResponseDtos;


    public TvDetailResponseDto(Media media, List<ReviewResponseDto> reviewResponseDtos, List<CastingResponseDto> castingList) {
        this.tvId = media.getId();
        this.title = media.getTitle();
        this.image = media.getImage();
        this.genre = media.getGenre();
        this.star = media.getStar();
        this.age = media.getAge();
        this.information = media.getInformation();
        this.castingResponseDtos = castingList;
        this.reviewResponseDtos = reviewResponseDtos;
    }
}


