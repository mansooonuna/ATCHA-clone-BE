package com.sparta.atchaclonecoding.domain.movie.service;

import com.sparta.atchaclonecoding.domain.movie.dto.MovieDetailResponseDto;
import com.sparta.atchaclonecoding.domain.movie.dto.MovieResponseDto;
import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import com.sparta.atchaclonecoding.domain.movie.repository.MovieRepository;
import com.sparta.atchaclonecoding.security.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import com.sparta.atchaclonecoding.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    @Transactional
    public ResponseEntity<List<MovieResponseDto>> getMovieList(UserDetailsImpl userDetails) {
        List<MovieResponseDto> movieResponseDtoList = movieRepository.findAll()
                                                        .stream()
                                                        .map(MovieResponseDto::new)
                                                        .collect(Collectors.toList());
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "영화 전체 조회 성공" , movieResponseDtoList), HttpStatus.OK);
    }

    public ResponseEntity<MovieDetailResponseDto> getMovie(Long movieId, UserDetailsImpl userDetails) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new NoSuchElementException("영화가 존재하지 않습니다.")
        );
        MovieDetailResponseDto movieDetailResponseDto = new MovieDetailResponseDto(movie);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "", movieDetailResponseDto), HttpStatus.OK);
    }
}
