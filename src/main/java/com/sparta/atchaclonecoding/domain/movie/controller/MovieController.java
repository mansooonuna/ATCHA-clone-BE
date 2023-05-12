package com.sparta.atchaclonecoding.domain.movie.controller;

import com.sparta.atchaclonecoding.domain.movie.dto.MovieDetailResponseDto;
import com.sparta.atchaclonecoding.domain.movie.dto.MovieResponseDto;
import com.sparta.atchaclonecoding.domain.movie.service.MovieService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/atcha")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponseDto>> movies(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return movieService.getMovieList(userDetails.getMember());
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieDetailResponseDto> movies(@PathVariable Long movieId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return movieService.getMovie(movieId, userDetails.getMember());
    }

}
