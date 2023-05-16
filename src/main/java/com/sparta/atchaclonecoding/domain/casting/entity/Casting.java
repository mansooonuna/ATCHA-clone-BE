package com.sparta.atchaclonecoding.domain.casting.entity;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Casting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "casting_id")
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column
    private String role;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @Column(name = "profile_img_url",nullable = false)
    private String image;


    public void addMedia(Media media) {
        this.media =media;
    }
}
