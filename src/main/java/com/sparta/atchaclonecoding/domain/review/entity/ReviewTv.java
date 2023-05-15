package com.sparta.atchaclonecoding.domain.review.entity;

import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewRequestDto;
import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ReviewTv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double star;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "tv_id")
    private Tv tv;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public ReviewTv(Tv tv, ReviewRequestDto requestDto, Member member) {
        this.star = requestDto.getStar();
        this.content = requestDto.getContent();
        this.tv = tv;
        this.member = member;
    }

    public void update(ReviewRequestDto requestDto) {
        this.star = requestDto.getStar();
        this.content = requestDto.getContent();
    }
}
