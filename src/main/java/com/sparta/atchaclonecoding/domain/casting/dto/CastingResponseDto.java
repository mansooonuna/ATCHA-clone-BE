package com.sparta.atchaclonecoding.domain.casting.dto;

import com.sparta.atchaclonecoding.domain.casting.entity.Casting;
import com.sparta.atchaclonecoding.domain.media.entity.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CastingResponseDto {
    private Long id;
    private String role;
    private Media media;
    private String image;


    public CastingResponseDto(Casting casting) {
        this.id = casting.getId();
        this.role = casting.getRole();
        this.media = casting.getMedia();
        this.image = casting.getImage();
    }
}
