package com.sparta.atchaclonecoding.domain.person.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PersonTv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_tv_id")
    private Long id;
    @Column(nullable = false)
    private String role;
    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;

}
