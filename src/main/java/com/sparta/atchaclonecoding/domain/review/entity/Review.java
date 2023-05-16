package com.sparta.atchaclonecoding.domain.review.entity;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE review SET is_deleted = true WHERE id = ? ")
public class Review extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column
    private double star;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isDeleted = Boolean.FALSE;

    public Review(Media media, ReviewRequestDto requestDto, Member member) {
        this.star = requestDto.getStar();
        this.content = requestDto.getContent();
        this.media = media;
        this.member = member;
    }

    public void update(ReviewRequestDto requestDto) {
        this.star = requestDto.getStar();
        this.content = requestDto.getContent();
    }
}
