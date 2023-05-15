package com.sparta.atchaclonecoding.domain.tv.repository;

import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TvRepository extends JpaRepository<Tv, Long> {

    @Query(value = "SELECT t FROM Tv t WHERE t.title LIKE %:searchKeyword%")
    List<Tv> findAllBySearchKeyword(@Param("searchKeyword") String searchKeyword);
}
