package com.sparta.atchaclonecoding.domain.movie.dto;

import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MovieResponseDto {
    private Long movieId;
    private String image;
    private String title;
    private String genre;
    private double star;

    public MovieResponseDto(Movie movie){
        this.movieId = movie.getId();
        this.image = movie.getImage();
        this.title = movie.getTitle();
        this.genre = movie.getGenre();
        this.star = movie.getStar();
    }
}
