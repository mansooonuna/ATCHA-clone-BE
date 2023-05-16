package com.sparta.atchaclonecoding.domain.casting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.atchaclonecoding.domain.casting.entity.Casting;
import java.util.List;

public interface CastingRepository extends JpaRepository<Casting, Long> {
    List<Casting> findAllById (Long mediaId);
}