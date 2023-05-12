package com.sparta.atchaclonecoding.domain.tv.controller;

import com.sparta.atchaclonecoding.domain.tv.sevice.TvService;
import com.sparta.atchaclonecoding.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/atcha")
public class TvController {

    private final TvService tvService;

    // Tv 전체조회
    @GetMapping("/tvs")
    public ResponseEntity<Message> getTvPrograms() {
        return tvService.getTvPrograms();
    }

    // Tv 상세조회
    @GetMapping("/tvs/{tv-id}")
    public ResponseEntity<Message> getTvProgram(@PathVariable(name = "tv-id")Long tvId) {
        return tvService.getTvProgram(tvId);
    }

}
