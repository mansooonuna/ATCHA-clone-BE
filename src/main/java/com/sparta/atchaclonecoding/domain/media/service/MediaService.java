package com.sparta.atchaclonecoding.domain.media.service;

import com.sparta.atchaclonecoding.domain.casting.dto.CastingResponseDto;
import com.sparta.atchaclonecoding.domain.casting.repository.CastingRepository;
import com.sparta.atchaclonecoding.domain.media.dto.MediaRecommendResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.MediaResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.MovieDetailResponseDto;
import com.sparta.atchaclonecoding.domain.media.dto.TvDetailResponseDto;
import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.entity.MediaType;
import com.sparta.atchaclonecoding.domain.media.repository.MediaRepository;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewResponseDto;
import com.sparta.atchaclonecoding.domain.review.entity.Review;
import com.sparta.atchaclonecoding.domain.review.repository.ReviewRepository;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.util.Message;
import com.sparta.atchaclonecoding.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.atchaclonecoding.exception.ErrorCode.MOVIE_NOT_FOUND;
import static com.sparta.atchaclonecoding.exception.ErrorCode.TV_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MediaRepository mediaRepository;
    private final CastingRepository castingRepository;
    private final ReviewRepository reviewRepository;

    // 전체 조회
    @Transactional
    public ResponseEntity<Page<Media>> getMediaList(Member member, Pageable pageable) {
        Page<Media> mediaPage = mediaRepository.findAll(pageable);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "전체 조회 성공", mediaPage), HttpStatus.OK);
    }

    // 전체 조회 - 영화
    @Transactional
    public ResponseEntity<Page<Media>> getMovies(Member member,  Pageable pageable) {
        Page<Media> mediaMoviePage = mediaRepository.findAllByCategory(MediaType.MOVIE, pageable);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "영화 전체 조회 성공", mediaMoviePage), HttpStatus.OK);
    }

    // 전체 조회 - TV
    @Transactional
    public ResponseEntity<Page<Media>> getTvs(Member member, Pageable pageable) {
        Page<Media> mediaTvPage = mediaRepository.findAllByCategory(MediaType.TV, pageable);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "TV 전체 조회 성공", mediaTvPage), HttpStatus.OK);
    }

    // 상세 조회 - 영화
    @Transactional
    public ResponseEntity<MovieDetailResponseDto> getMovie(Long movieId, Member member) {
        Media mediaMovie = mediaRepository.findByIdAndCategory(movieId, MediaType.MOVIE).orElseThrow(
                () -> new CustomException(MOVIE_NOT_FOUND)
        );
        List<CastingResponseDto> castingResponseDto = castingRepository.findAllByMediaId(movieId).stream().map(CastingResponseDto::new).collect(Collectors.toList());
        List<ReviewResponseDto> reviewResponseDto = reviewRepository.findAllByMediaId(movieId).stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed()).map(ReviewResponseDto::new).collect(Collectors.toList());
        MovieDetailResponseDto movieDetailResponseDto = new MovieDetailResponseDto(mediaMovie, reviewResponseDto, castingResponseDto);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "영화 상세 조회 성공", movieDetailResponseDto), HttpStatus.OK);
    }

    // 상세 조회 - TV
    @Transactional
    public ResponseEntity<TvDetailResponseDto> getTv(Long tvId, Member member) {
        Media mediaTv = mediaRepository.findByIdAndCategory(tvId, MediaType.TV).orElseThrow(
                () -> new CustomException(TV_NOT_FOUND)
        );
        List<CastingResponseDto> castingResponseDto = castingRepository.findAllByMediaId(tvId).stream().map(CastingResponseDto::new).collect(Collectors.toList());
        List<ReviewResponseDto> reviewResponseDto = reviewRepository.findAllByMediaId(tvId).stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed()).map(ReviewResponseDto::new).collect(Collectors.toList());
        TvDetailResponseDto tvDetailResponseDto = new TvDetailResponseDto(mediaTv, reviewResponseDto, castingResponseDto);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "티비 상세 조회 성공", tvDetailResponseDto), HttpStatus.OK);
    }

    // 전체 검색
    @Transactional
    public ResponseEntity<Message> searchTv(String searchKeyword, Member member) {
        List<MediaResponseDto> medias = mediaRepository.findAllBySearchKeyword(searchKeyword).stream().map(MediaResponseDto::new).toList();

        Message message;
        if (medias.isEmpty()) {
            message = Message.setSuccess(StatusEnum.OK, "검색 결과 없음", medias);
        }
        message = Message.setSuccess(StatusEnum.OK, "요청 성공", medias);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 영화/TV 추천
    public ResponseEntity<MediaRecommendResponseDto> recommendAll(Member member) {
        List<MediaRecommendResponseDto> medias = mediaRepository.findAllByOrderByStarDesc().stream().map(MediaRecommendResponseDto::new).toList();
        if (medias.size() > 8) {
            medias = medias.subList(0, 8);
        }
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "추천 영화/TV 조회 성공", medias), HttpStatus.OK);
    }
}
