package com.sparta.atchaclonecoding.domain.media.entity;

public enum MediaType {
    MOVIE("movie"),
    TV("tv");

    String mediaType;

    MediaType(String mediaType){
        this.mediaType = mediaType;
    }

    public String getMedia(){
        return mediaType;
    }
}
