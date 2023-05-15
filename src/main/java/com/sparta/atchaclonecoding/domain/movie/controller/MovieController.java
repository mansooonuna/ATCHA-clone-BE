package com.sparta.atchaclonecoding.domain.movie.controller;

import com.sparta.atchaclonecoding.domain.movie.dto.MovieDetailResponseDto;
import com.sparta.atchaclonecoding.domain.movie.dto.MovieResponseDto;
import com.sparta.atchaclonecoding.domain.movie.service.MovieService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atcha")
@RequiredArgsConstructor
@Tag(name = "MovieController", description = "영화 관련 API")
public class MovieController {
    private final MovieService movieService;

    @Operation(summary = "영화 조회 메서드", description = "영화 목록 조회하는 메서드입니다.")
    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponseDto>> movies(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return movieService.getMovieList(userDetails.getMember());
    }
    @Operation(summary = "영화 상세 조회 메서드", description = "선택한 영화를 상세 조회하는 메서드입니다.")
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieDetailResponseDto> movies(@PathVariable Long movieId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return movieService.getMovie(movieId, userDetails.getMember());
    }

    @Operation(summary = "영화 검색 메서드", description = "영화 검색하는 메서드입니다.")
    @GetMapping("/tvs/search")
    public ResponseEntity<Message> searchMovie(@RequestParam(value = "searchKeyword") String searchKeyword) {
        return movieService.searchMovie(searchKeyword);
    }
}
