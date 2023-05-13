package com.sparta.atchaclonecoding.domain.person.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_movie_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String role;
//    @ManyToOne
//    @JoinColumn(name = "person_id")
//    @JsonBackReference
//    private Person person;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    public void addMovie(Movie movie){
        this.movie = movie;
    }
}
