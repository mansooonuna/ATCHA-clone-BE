package com.sparta.atchaclonecoding.domain.person.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
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
public class PersonTv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_tv_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String role;
//    @ManyToOne
//    @JoinColumn(name = "person_id")
//    @JsonBackReference
//    private Person person;

    @ManyToOne
    @JoinColumn(name = "tv_id")
    @JsonBackReference
    private Tv tv;

    public void addTv(Tv tv){
        this.tv = tv;
    }

}
