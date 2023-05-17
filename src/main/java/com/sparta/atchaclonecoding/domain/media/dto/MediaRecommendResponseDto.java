package com.sparta.atchaclonecoding.domain.media.dto;

import com.sparta.atchaclonecoding.domain.media.entity.Media;
import com.sparta.atchaclonecoding.domain.media.entity.MediaType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MediaRecommendResponseDto {
    private Long id;
    private MediaType category;
    private String image;
    private String title;

    public MediaRecommendResponseDto(Media media){
        this.id = media.getId();
        this.category = media.getCategory();
        this.image = media.getImage();
        this.title = media.getTitle();
    }
}
