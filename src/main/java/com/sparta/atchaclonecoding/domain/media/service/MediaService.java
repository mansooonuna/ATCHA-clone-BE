package com.sparta.atchaclonecoding.domain.media.service;

import com.sparta.atchaclonecoding.domain.casting.dto.CastingResponseDto;
import com.sparta.atchaclonecoding.domain.casting.repository.CastingRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.atchaclonecoding.exception.ErrorCode.MOVIE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MediaRepository mediaRepository;
    private final CastingRepository castingRepository;
    private final ReviewRepository reviewRepository;

    // 전체 조회
    @Transactional
    public ResponseEntity<List<MediaResponseDto>> getMediaList(Member member) {
        List<MediaResponseDto> mediaResponseDtoList = mediaRepository.findAll()
                                                        .stream()
                                                        .map(MediaResponseDto::new)
                                                        .collect(Collectors.toList());
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "전체 조회 성공" , mediaResponseDtoList), HttpStatus.OK);
    }

    // 전체 조회 - 영화
    @Transactional
    public ResponseEntity<List<MediaResponseDto>> getTvs(Member member) {
        List<MediaResponseDto> mediaResponseDtoList = mediaRepository.findAllByCategory(MediaType.MOVIE)
                                                        .stream()
                                                        .map(MediaResponseDto::new)
                                                        .collect(Collectors.toList());
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "영화 전체 조회 성공" , mediaResponseDtoList), HttpStatus.OK);
    }

    // 전체 조회 - TV
    @Transactional
    public ResponseEntity<List<MediaResponseDto>> getMovies(Member member) {
        List<MediaResponseDto> mediaResponseDtoList = mediaRepository.findAllByCategory(MediaType.TV)
                                                        .stream()
                                                        .map(MediaResponseDto::new)
                                                        .collect(Collectors.toList());
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "TV 전체 조회 성공" , mediaResponseDtoList), HttpStatus.OK);
    }


    // 상세 조회 - 영화
    @Transactional
    public ResponseEntity<MovieDetailResponseDto> getMovie(Long movieId, Member member) {
        Media mediaMovie = mediaRepository.findByIdAndCategory(movieId, MediaType.MOVIE).orElseThrow(
                () -> new CustomException(MOVIE_NOT_FOUND)
        );
        List<CastingResponseDto> castingResponseDto = castingRepository.findAllById(movieId).stream().map(CastingResponseDto::new).collect(Collectors.toList());
        List<ReviewResponseDto> reviewResponseDto = reviewRepository.findAllById(movieId).stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed()).map(ReviewResponseDto::new).collect(Collectors.toList());
        MovieDetailResponseDto movieDetailResponseDto = new MovieDetailResponseDto(mediaMovie, reviewResponseDto, castingResponseDto);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "영화 상세 조회 성공", movieDetailResponseDto), HttpStatus.OK);
    }

    // 상세 조회 - TV
    @Transactional
    public ResponseEntity<TvDetailResponseDto> getTv(Long tvId, Member member) {
        Media mediaMovie = mediaRepository.findByIdAndCategory(tvId, MediaType.TV).orElseThrow(
                () -> new CustomException(MOVIE_NOT_FOUND)
        );
        List<CastingResponseDto> castingResponseDto = castingRepository.findAllById(tvId).stream().map(CastingResponseDto::new).collect(Collectors.toList());
        List<ReviewResponseDto> reviewResponseDto = reviewRepository.findAllById(tvId).stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed()).map(ReviewResponseDto::new).collect(Collectors.toList());
        TvDetailResponseDto tvDetailResponseDto = new TvDetailResponseDto(mediaMovie, reviewResponseDto, castingResponseDto);
        return new ResponseEntity(Message.setSuccess(StatusEnum.OK, "티비 상세 조회 성공", tvDetailResponseDto), HttpStatus.OK);
    }

    // 전체 검색
    @Transactional
    public ResponseEntity<Message> searchTv(String searchKeyword) {
        List<MediaResponseDto> medias = mediaRepository.findAllBySearchKeyword(searchKeyword).stream().map(MediaResponseDto::new).toList();

        Message message;
        if (medias.isEmpty()) {
            message = Message.setSuccess(StatusEnum.OK, "검색 결과 없음", medias);
        }
        message = Message.setSuccess(StatusEnum.OK, "요청성공", medias);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
