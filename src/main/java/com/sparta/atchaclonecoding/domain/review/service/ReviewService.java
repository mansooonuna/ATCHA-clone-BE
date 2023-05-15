package com.sparta.atchaclonecoding.domain.review.service;

import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import com.sparta.atchaclonecoding.domain.review.entity.ReviewTv;
import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
import com.sparta.atchaclonecoding.domain.movie.repository.MovieRepository;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewRequestDto;
import com.sparta.atchaclonecoding.domain.review.entity.ReviewMovie;
import com.sparta.atchaclonecoding.domain.review.repository.ReviewMovieRepository;
import com.sparta.atchaclonecoding.domain.review.repository.ReviewTvRepository;
import com.sparta.atchaclonecoding.domain.tv.repository.TvRepository;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.util.Message;
import com.sparta.atchaclonecoding.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.atchaclonecoding.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMovieRepository reviewMovieRepository;
    private final ReviewTvRepository reviewTvRepository;
    private final MovieRepository movieRepository;
    private final TvRepository tvRepository;

    // 영화 리뷰 작성
    public ResponseEntity<Message> addReviewMovie(Long movieId, ReviewRequestDto requestDto, Member member) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new CustomException(MOVIE_NOT_FOUND)
        );
        if (requestDto.getContent().equals("")) {
            throw new CustomException(NON_CONTENT);
        }
        ReviewMovie reviewMovie = new ReviewMovie(movie, requestDto, member);
        reviewMovieRepository.save(reviewMovie);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 작성 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // TV 리뷰 작성
    public ResponseEntity<Message> addReviewTv(Long tvId, ReviewRequestDto requestDto, Member member) {
        Tv tv = tvRepository.findById(tvId).orElseThrow(
                () -> new CustomException(TV_NOT_FOUND)
        );
        if (requestDto.getContent().equals("")) {
            throw new CustomException(NON_CONTENT);
        }
        ReviewTv reviewTv = new ReviewTv(tv, requestDto, member);
        reviewTvRepository.save(reviewTv);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 작성 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Movie 리뷰 수정
    public ResponseEntity<Message> updateReviewMovie(Long movieReviewId, ReviewRequestDto requestDto, Member member) {
        ReviewMovie findMovieReview = reviewMovieRepository.findById(movieReviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );
        findMovieReview.update(requestDto);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 수정 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Tv 리뷰 수정
    public ResponseEntity<Message> updateReviewTv(Long tvReviewId, ReviewRequestDto requestDto, Member member) {
        ReviewTv findTvReview = reviewTvRepository.findById(tvReviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );
        findTvReview.update(requestDto);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 수정 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Message> deleteReviewMovie(Long movieReviewId, Member member) {
        ReviewMovie findMovieReview = reviewMovieRepository.findById(movieReviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );

        if (findMovieReview.isDeleted()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }

        reviewMovieRepository.deleteById(movieReviewId);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 삭제 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Message> deleteReviewTv(Long tvReviewId, Member member) {
        ReviewTv findTvReview = reviewTvRepository.findById(tvReviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );

        if (findTvReview.isDeleted()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }

        reviewTvRepository.deleteById(tvReviewId);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 삭제 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
