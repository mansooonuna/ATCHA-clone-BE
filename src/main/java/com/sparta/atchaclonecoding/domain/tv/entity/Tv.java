package com.sparta.atchaclonecoding.domain.tv.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_id")
    private Long id;
    @Column
    private String title;
    @Column
    private double star;
    @Column
    private String genre;
    @Column
    private String age;
    @Column(name = "image_url", columnDefinition = "longtext")
    @Lob
    private String image;
    @Column(columnDefinition = "text")
    private String information;

}
