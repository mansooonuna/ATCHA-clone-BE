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
public class MovieDetailResponseDto {
    private Long movieId;
    private MediaType category;
    private String title;
    private String image;
    private String time;
    private String genre;
    private double star;
    private String age;
    private String information;
    private List<CastingResponseDto> castingList;
    private List<ReviewResponseDto> reviewList;

    public MovieDetailResponseDto(Media media, List<ReviewResponseDto> reviewList, List<CastingResponseDto> castingList) {
        this.movieId = media.getId();
        this.category = media.getCategory();
        this.title = media.getTitle();
        this.image = media.getImage();
        this.time = media.getTime();
        this.genre = media.getGenre();
        this.star = media.getStar();
        this.age = media.getAge();
        this.information = media.getInformation();
        this.castingList = castingList;
        this.reviewList = reviewList;
    }

}
