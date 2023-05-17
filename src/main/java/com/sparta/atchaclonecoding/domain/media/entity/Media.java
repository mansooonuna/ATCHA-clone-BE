package com.sparta.atchaclonecoding.domain.media.entity;

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
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long id;
    @Column
    private String title;
    @Column
    private String genre;
    @Column
    private String time;
    @Column
    private String age;
    @Column(columnDefinition = "text")
    private String information;
    @Column(name = "image_url", columnDefinition = "longtext")
    @Lob
    private String image;
    @Column
    private double star;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaType category;

    public void deleteStar(){
        this.star = 0;
    }

    public void updateStar(double star){
        this.star = star;
    }

}
