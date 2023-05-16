package com.sparta.atchaclonecoding.domain.casting.repository;

import com.sparta.atchaclonecoding.domain.casting.entity.Casting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CastingRepository extends JpaRepository<Casting, Long> {
    List<Casting> findAllByMediaId(Long mediaId);
}