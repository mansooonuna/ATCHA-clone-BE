package com.sparta.atchaclonecoding.domain.movie.dto;

import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import com.sparta.atchaclonecoding.domain.person.entity.PersonMovie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<PersonMovie> personMovieList;

    public MovieDetailResponseDto(Movie movie) {
        this.movieId = movie.getId();
        this.title = movie.getTitle();
        this.image = movie.getImage();
        this.time = movie.getTime();
        this.genre = movie.getGenre();
        this.star = movie.getStar();
        this.age = movie.getAge();
        this.information = movie.getInformation();
        this.personMovieList = movie.getPersonMovieList();
    }

}
