package com.sparta.atchaclonecoding.domain.media.repository;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.entity.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query(value = "SELECT m FROM Media m WHERE m.title LIKE %:searchKeyword%")
    List<Media> findAllBySearchKeyword(@Param("searchKeyword") String searchKeyword);
    Optional<Media> findByIdAndCategory(Long id, MediaType mediaType);
    Page<Media> findAllByCategory(MediaType mediaType, Pageable pageable);
    List<Media> findAllByOrderByStarDesc();
}
