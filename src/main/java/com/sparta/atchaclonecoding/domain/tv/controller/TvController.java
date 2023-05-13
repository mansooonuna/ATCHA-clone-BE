package com.sparta.atchaclonecoding.domain.tv.controller;

import com.sparta.atchaclonecoding.domain.tv.sevice.TvService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/atcha")
@Tag(name = "TvController", description = "TV 관련 API")
public class TvController {

    private final TvService tvService;

    // Tv 전체조회
    @Operation(summary = "TV 조회 메서드", description = "TV 프로그램 목록 조회하는 메서드입니다.")
    @GetMapping("/tvs")
    public ResponseEntity<Message> getTvPrograms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return tvService.getTvPrograms(userDetails.getMember());
    }

    // Tv 상세조회
    @Operation(summary = "TV 상세 조회 메서드", description = "선택한 TV 프로그램의 상세 조회하는 메서드입니다.")
    @GetMapping("/tvs/{tv-id}")
    public ResponseEntity<Message> getTvProgram(@PathVariable(name = "tv-id")Long tvId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return tvService.getTvProgram(tvId, userDetails.getMember());
    }

    @Operation(summary = "TV 프로그램 검색 메서드", description = "TV 프로그램을 검색하는 메서드입니다.")
    @GetMapping("/tvs/search")
    public ResponseEntity<Message> searchTv(@RequestParam(value = "searchKeyword") String searchKeyword) {
        return tvService.searchTv(searchKeyword);
    }


}
