package com.sparta.atchaclonecoding.domain.person.repository;

import com.sparta.atchaclonecoding.domain.person.entity.PersonMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonMovieRepository extends JpaRepository<PersonMovie, Long> {
}
