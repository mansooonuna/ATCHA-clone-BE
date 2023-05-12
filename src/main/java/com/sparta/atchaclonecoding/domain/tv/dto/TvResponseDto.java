package com.sparta.atchaclonecoding.domain.tv.dto;

import com.sparta.atchaclonecoding.domain.person.entity.Person;
import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Getter;

import java.util.List;

@Getter
public class TvResponseDto {
    private Long tvId;
    private String image;
    private String title;
    private String genre;
    private double star;

    public TvResponseDto(Tv tv) {
        this.tvId = tv.getId();
        this.image = tv.getImage();
        this.title = tv.getTitle();
        this.genre = tv.getGenre();
        this.star = tv.getStar();
    }
}
