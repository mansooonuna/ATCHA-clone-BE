package com.sparta.atchaclonecoding.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @JsonIgnore
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "image_url")
    @Lob
    private String image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtArY0iIz1b6rGdZ6xkSegyALtWQKBjupKJQ&usqp=CAU";

    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
