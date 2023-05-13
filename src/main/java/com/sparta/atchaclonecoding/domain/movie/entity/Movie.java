package com.sparta.atchaclonecoding.domain.movie.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.atchaclonecoding.domain.person.entity.PersonMovie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;
    @Column
    private String title;
    @Column
    private String genre;
    @Column
    private String time;
    @Column
    private String age;
    @Column(columnDefinition = "text")
    private String information;
    @Column(name = "image_url", columnDefinition = "longtext")
    @Lob
    private String image;
    @Column
    private double star;

    // TODO : 테이블 생성 후 List 잘 받아오는지 확인 후 추가 or 삭제 진행
    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<PersonMovie> personMovieList;

}
