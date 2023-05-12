package com.sparta.atchaclonecoding.domain.person.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "profile_img_url",nullable = false)
    private String image;
}
