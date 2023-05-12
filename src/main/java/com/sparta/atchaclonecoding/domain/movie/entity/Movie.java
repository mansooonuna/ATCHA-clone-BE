package com.sparta.atchaclonecoding.domain.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private String age;
    @Column(nullable = false, columnDefinition = "text")
    private String information;
    @Column(name = "image_url", nullable = false, columnDefinition = "text")
    private String image;
    @Column(nullable = false)
    private double star;

}
