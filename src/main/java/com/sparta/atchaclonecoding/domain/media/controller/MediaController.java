package com.sparta.atchaclonecoding.domain.media.controller;

import com.sparta.atchaclonecoding.domain.media.dto.MediaRecommendResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.MediaResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.MovieDetailResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.TvDetailResponseDto;
import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.service.MediaService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atcha")
@RequiredArgsConstructor
@Tag(name = "MediaController", description = "영화 및 TV 관련 API")
public class MediaController {
    private final MediaService mediaService;

    @Operation(summary = "전체 조회 메서드", description = "영화/TV 전체 목록 조회하는 메서드입니다.")
    @GetMapping("/all")
    public ResponseEntity<Page<Media>> medias(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return mediaService.getMediaList(userDetails.getMember(), pageable);
    }

    @Operation(summary = "영화 전체 조회 메서드", description = "영화 전체 목록 조회하는 메서드입니다.")
    @GetMapping("/movies")
    public ResponseEntity<Page<Media>> movies(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return mediaService.getMovies(userDetails.getMember(), pageable);
    }

    @Operation(summary = "TV 전체 조회 메서드", description = "TV 전체 목록 조회하는 메서드입니다.")
    @GetMapping("/tvs")
    public ResponseEntity<Page<Media>> tvs(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return mediaService.getTvs(userDetails.getMember(), pageable);
    }

    @Operation(summary = "영화 상세 조회 메서드", description = "선택한 영화를 상세 조회하는 메서드입니다.")
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieDetailResponseDto> movie(@PathVariable Long movieId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mediaService.getMovie(movieId, userDetails.getMember());
    }

    @Operation(summary = "TV 상세 조회 메서드", description = "선택한 TV 프로그램을 상세 조회하는 메서드입니다.")
    @GetMapping("/tvs/{tvId}")
    public ResponseEntity<TvDetailResponseDto> tv(@PathVariable Long tvId,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mediaService.getTv(tvId, userDetails.getMember());
    }

    @Operation(summary = "전체 검색 메서드", description = "영화와 TV 프로그램을 검색하는 메서드입니다.")
    @GetMapping("/search")
    public ResponseEntity<Message> searchAll(@RequestParam(value = "searchKeyword") String searchKeyword, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mediaService.searchTv(searchKeyword, userDetails.getMember());
    }

    @Operation(summary = "검색 기본 화면 추천 메서드", description = "검색 기본 화면에서 별점순으로 영화/TV 추천해주는 메서드입니다.")
    @GetMapping("/search/all")
    public ResponseEntity<MediaRecommendResponseDto> recommendAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mediaService.recommendAll(userDetails.getMember());
    }



}
