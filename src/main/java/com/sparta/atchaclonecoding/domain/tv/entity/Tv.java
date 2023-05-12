package com.sparta.atchaclonecoding.domain.tv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Tv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private double star;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String age;
    @Column(name = "image_url", nullable = false)
    @Lob
    private String image;
    @Column(nullable = false,columnDefinition = "text")
    private String information;

}
