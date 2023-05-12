package com.sparta.atchaclonecoding.domain.tv.dto;

import com.sparta.atchaclonecoding.domain.person.entity.PersonMovie;
import com.sparta.atchaclonecoding.domain.person.entity.PersonTv;
import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
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
    private List<PersonTv> personTvList;


    public TvDetailResponseDto(Tv tv, List<PersonTv> personTvList) {
        this.tvId = tv.getId();
        this.title = tv.getTitle();
        this.star = tv.getStar();
        this.genre = tv.getGenre();
        this.age = tv.getAge();
        this.image = tv.getImage();
        this.information = tv.getInformation();
        this.personTvList = personTvList;
    }
}


