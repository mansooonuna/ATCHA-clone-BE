package com.sparta.atchaclonecoding.domain.media.repository;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.entity.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query(value = "SELECT t FROM Media t WHERE t.title LIKE %:searchKeyword%")
    List<Media> findAllBySearchKeyword(@Param("searchKeyword") String searchKeyword);

    Optional<Media> findByIdAndCategory(Long id, MediaType mediaType);

    List<Media> findAllByCategory(MediaType mediaType);

    List<Media> findAllByOrderByStarDesc();

}
