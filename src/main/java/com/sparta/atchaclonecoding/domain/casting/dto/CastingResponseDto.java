package com.sparta.atchaclonecoding.domain.casting.dto;

import com.sparta.atchaclonecoding.domain.casting.entity.Casting;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CastingResponseDto {
    private Long id;
    private String role;
    private String name;
    private String image;


    public CastingResponseDto(Casting casting) {
        this.id = casting.getId();
        this.role = casting.getRole();
        this.name = casting.getName();
        this.image = casting.getImage();
    }
}
