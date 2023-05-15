package com.sparta.atchaclonecoding.domain.movie.repository;

import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT t FROM Tv t WHERE t.title LIKE %:searchKeyword%")
    List<Movie> findAllBySearchKeyword(@Param("searchKeyword") String searchKeyword);
}
