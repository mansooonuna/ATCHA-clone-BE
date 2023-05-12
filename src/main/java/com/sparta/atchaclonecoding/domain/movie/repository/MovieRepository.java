package com.sparta.atchaclonecoding.domain.movie.repository;

import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
