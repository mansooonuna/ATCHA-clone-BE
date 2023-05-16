package com.sparta.atchaclonecoding.domain.review.repository;

import com.sparta.atchaclonecoding.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllById (Long mediaId);
}
