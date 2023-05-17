package com.sparta.atchaclonecoding.domain.review.repository;

import com.sparta.atchaclonecoding.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByMediaId (Long mediaId);

    @Query("select star from Review")
    List<Double> selectStar();
}
