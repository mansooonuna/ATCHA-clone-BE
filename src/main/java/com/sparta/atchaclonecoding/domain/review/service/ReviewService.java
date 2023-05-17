package com.sparta.atchaclonecoding.domain.review.service;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.repository.MediaRepository;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.review.entity.Review;
import com.sparta.atchaclonecoding.domain.review.repository.ReviewRepository;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewRequestDto;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.util.Message;
import com.sparta.atchaclonecoding.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.atchaclonecoding.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MediaRepository mediaRepository;

    // 리뷰 작성
    public ResponseEntity<Message> addReviewMovie(Long mediaId, ReviewRequestDto requestDto, Member member) {
        Media media = getMedia(mediaId);

        if (requestDto.getContent().equals("")) {
            log.info("리뷰 작성 내용이 없음");
            throw new CustomException(NON_CONTENT);
        }
        Review reviewMovie = new Review(media, requestDto, member);
        reviewRepository.save(reviewMovie);
        media.deleteStar();
        List<Review> reviews = reviewRepository.findAll();
        double star = 0;
        List<Double> stars = reviewRepository.selectStar();
        for(int i=0; i<stars.size(); i++){
            star += stars.get(i);
        }
        star = Math.floor(star/reviews.size()*10);
        media.updateStar(star/10.0);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 작성 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 리뷰 수정
    public ResponseEntity<Message> updateReviewMovie(Long mediaId, Long mediaReviewId, ReviewRequestDto requestDto, Member member) {
        Media media = getMedia(mediaId);

        Review findMovieReview = getReview(mediaReviewId);
        findMovieReview.update(requestDto);
        media.deleteStar();
        List<Review> reviews = reviewRepository.findAll();
        double star = 0;
        List<Double> stars = reviewRepository.selectStar();
        for(int i=0; i<stars.size(); i++){
            star += stars.get(i);
        }
        star = Math.floor(star/reviews.size()*10);
        media.updateStar(star/10.0);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 수정 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    public ResponseEntity<Message> deleteReviewMovie(Long mediaId, Long mediaReviewId, Member member) {
        Media media = getMedia(mediaId);

        Review findMovieReview = getReview(mediaReviewId);
        if (findMovieReview.isDeleted()) {
            log.info("리뷰가 이미 삭제됨");
            throw new CustomException(REVIEW_NOT_FOUND);
        }

        reviewRepository.deleteById(mediaReviewId);
        media.deleteStar();
        List<Review> reviews = reviewRepository.findAll();
        double star = 0;
        List<Double> stars = reviewRepository.selectStar();
        for(int i=0; i<stars.size(); i++){
            star += stars.get(i);
        }
        star = Math.floor(star/reviews.size()*10);
        media.updateStar(star/10.0);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 삭제 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private Media getMedia(Long mediaId){
        return mediaRepository.findById(mediaId).orElseThrow(
                () -> new CustomException(MOVIE_NOT_FOUND)
        );
    }

    private Review getReview(Long mediaReviewId){
        return reviewRepository.findById(mediaReviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );
    }

}
