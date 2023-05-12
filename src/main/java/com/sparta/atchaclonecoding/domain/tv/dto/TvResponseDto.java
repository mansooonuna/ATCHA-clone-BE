package com.sparta.atchaclonecoding.domain.tv.dto;

import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
import lombok.Getter;

@Getter
public class TvResponseDto {
    private String title;
    private String image;
    private int year;
    private String genre;
    private String country;
    private double star;

    public TvResponseDto(Tv tv) {
        this.title = tv.getTitle();
        this.image = tv.getImage();
        this.year = tv.getYear();
        this.genre = tv.getGenre();
        this.country = tv.getCountry();
        this.star = tv.getStar();

    }


}
