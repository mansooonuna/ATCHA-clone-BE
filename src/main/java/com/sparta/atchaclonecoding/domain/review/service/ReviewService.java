package com.sparta.atchaclonecoding.domain.review.service;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.repository.MediaRepository;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewRequestDto;
import com.sparta.atchaclonecoding.domain.review.entity.Review;
import com.sparta.atchaclonecoding.domain.review.repository.ReviewRepository;
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
    public ResponseEntity<Message> addReviewMedia(Long mediaId, ReviewRequestDto requestDto, Member member) {
        Media media = getMedia(mediaId);

        if (requestDto.getContent().equals("")) {
            log.info("리뷰 작성 내용이 없음");
            throw new CustomException(NON_CONTENT);
        }
        Review reviewMovie = new Review(media, requestDto, member);
        reviewRepository.save(reviewMovie);
        updateStars(media);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 작성 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 리뷰 수정
    public ResponseEntity<Message> updateReviewMedia(Long mediaId, Long mediaReviewId, ReviewRequestDto requestDto, Member member) {
        Media media = getMedia(mediaId);

        Review findMovieReview = getReview(mediaReviewId);
        findMovieReview.update(requestDto);
        updateStars(media);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 수정 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    public ResponseEntity<Message> deleteReviewMedia(Long mediaId, Long mediaReviewId, Member member) {
        Media media = getMedia(mediaId);
        Review findMovieReview = getReview(mediaReviewId);
        reviewRepository.deleteById(mediaReviewId);
        updateStars(media);
        Message message = Message.setSuccess(StatusEnum.OK, "리뷰 삭제 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private Media getMedia(Long mediaId) {
        return mediaRepository.findById(mediaId).orElseThrow(
                () -> new CustomException(MEDIA_NOT_FOUND)
        );
    }

    //리뷰 유무 체크
    private Review getReview(Long mediaReviewId) {
        return reviewRepository.findById(mediaReviewId).orElseThrow(
                () -> new CustomException(REVIEW_NOT_FOUND)
        );
    }

    // 최신 리뷰 반영하여 별점 수정
    private void updateStars(Media media) {
        media.deleteStar();
        List<Review> reviews = reviewRepository.findAll();
        double star = 0;
        List<Double> stars = reviewRepository.selectStar();
        for (Double aDouble : stars) {
            star += aDouble;
        }
        star = Math.round(star / reviews.size() * 10) / 10.0;
        media.updateStar(star);
    }
}