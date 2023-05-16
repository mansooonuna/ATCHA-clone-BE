package com.sparta.atchaclonecoding.domain.media.dto;

import com.sparta.atchaclonecoding.domain.casting.dto.CastingResponseDto;
import com.sparta.atchaclonecoding.domain.casting.entity.Casting;
import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MovieDetailResponseDto {
    private Long movieId;
    private String title;
    private String image;
    private String time;
    private String genre;
    private double star;
    private String age;
    private String information;
    private List<CastingResponseDto> castingResponseDtos;
    private List<ReviewResponseDto> reviewResponseDtos;

    public MovieDetailResponseDto(Media media, List<ReviewResponseDto> reviewList, List<CastingResponseDto> castingList) {
        this.movieId = media.getId();
        this.title = media.getTitle();
        this.image = media.getImage();
        this.time = media.getTime();
        this.genre = media.getGenre();
        this.star = media.getStar();
        this.age = media.getAge();
        this.information = media.getInformation();
        this.castingResponseDtos = castingList;
        this.reviewResponseDtos = reviewList;
    }

}
