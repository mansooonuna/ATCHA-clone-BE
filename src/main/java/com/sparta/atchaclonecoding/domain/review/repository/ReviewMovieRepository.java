package com.sparta.atchaclonecoding.domain.review.repository;

import com.sparta.atchaclonecoding.domain.review.entity.ReviewMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewMovieRepository extends JpaRepository<ReviewMovie, Long> {
}
